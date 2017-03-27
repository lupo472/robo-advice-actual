package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.persistence.util.Value;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.HoltWinters;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.*;
import weka.filters.supervised.attribute.TSLagMaker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class ForecastingOperator extends AbstractOperator {

	private static final String VALUES = "values";
	private static final String DATE = "date";

	//TODO working on demo
	@Cacheable("demo")
	public List<PortfolioDTO> getDemo(PeriodDTO period, Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		List<CustomStrategyEntity> strategyList = this.customStrategyRep.findByUserAndActive(user, true);
		CapitalEntity capital = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
		if(strategyList.isEmpty() || capital == null) {
			return null;
		}
		try {
			Map<Long, SortedMap<LocalDate, Map<Long, BigDecimal>>>  forecastPerClassMap = new HashMap<>();
			Map<Long, AssetClassEntity> assetClassMap = new HashMap<>();
			List<PortfolioEntity> portfolioEntityList = new ArrayList<>();
			for(CustomStrategyEntity strategy : strategyList) {
				BigDecimal capitalPerClass = capital.getAmount().divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
				List<AssetEntity> assets = this.assetRep.findByAssetClass(strategy.getAssetClass());
				SortedMap<LocalDate, Map<Long, BigDecimal>> assetForecastPerDateMap = this.getAssetForecastPerDateMap(assets, period.getPeriod());
				forecastPerClassMap.put(strategy.getAssetClass().getId(), assetForecastPerDateMap);
				assetClassMap.put(strategy.getAssetClass().getId(), strategy.getAssetClass());
				LocalDate date = assetForecastPerDateMap.firstKey();
				Map<Long, BigDecimal> forecastPerAssetMap = assetForecastPerDateMap.get(date);
				for(AssetEntity asset : assets) {
					PortfolioEntity entity = new PortfolioEntity();
					entity.setAssetClass(strategy.getAssetClass());
					entity.setAsset(asset);
					entity.setDate(date);
					BigDecimal value = capitalPerClass.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
					entity.setValue(value);
					BigDecimal units = value.divide(forecastPerAssetMap.get(asset.getId()), 8, RoundingMode.HALF_UP);
					entity.setUnits(units);
					portfolioEntityList.add(entity);
				}
				assetForecastPerDateMap.remove(date);
			}
			//TODO convertire in PortfolioDTO
			Map<Long, BigDecimal> valuePerAssetClassMap = new HashMap<>();
			for(PortfolioEntity entity : portfolioEntityList) {
				if(valuePerAssetClassMap.get(entity.getAssetClass().getId()) == null) {
					valuePerAssetClassMap.put(entity.getAssetClass().getId(), new BigDecimal(0));
				}
				valuePerAssetClassMap.put(entity.getAssetClass().getId(), valuePerAssetClassMap.get(entity.getAssetClass().getId()).add(entity.getValue()));
			}

			PortfolioDTO portfolio = this.portfolioWrap.wrapToDTO(portfolioEntityList, capital.getAmount(), valuePerAssetClassMap);
			List<PortfolioDTO> result = new ArrayList<>();
			result.add(portfolio);
			return result;
		} catch (Exception e) {
			System.out.println("eccezione " + e.getLocalizedMessage());
			return null;
		}
	}



	public SortedMap<LocalDate, Map<Long, BigDecimal>> getAssetForecastPerDateMap(List<AssetEntity> assets, int period) throws Exception {
		Map<Long, Map<LocalDate, BigDecimal>> forecastPerAssetMap = getForecastPerAsset(assets, period);
		SortedMap<LocalDate, Map<Long, BigDecimal>> assetForecastPerDateMap = new TreeMap<>();
		for(AssetEntity asset : assets) {
			Map<LocalDate, BigDecimal> valuePerDateMap = forecastPerAssetMap.get(asset.getId());
			for(LocalDate date : valuePerDateMap.keySet()) {
				if(assetForecastPerDateMap.get(date) == null) {
					assetForecastPerDateMap.put(date, new HashMap<>());
				}
				assetForecastPerDateMap.get(date).put(asset.getId(), valuePerDateMap.get(date));
			}
		}
		return assetForecastPerDateMap;
	}

	@Cacheable("forecast")
	public List<FinancialDataDTO> getForecast(PeriodDTO period) {
		List<AssetClassEntity> assetClassSet = this.assetClassRep.findAll();
		List<FinancialDataDTO> result = new ArrayList<>();
		try {
			for(AssetClassEntity assetClass : assetClassSet) {
				List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
				List<FinancialDataElementDTO> forecastPerClass = this.getForecastPerClass(assets, period.getPeriod());
				FinancialDataDTO financialData = new FinancialDataDTO();
				financialData.setAssetClass(this.assetClassConv.convertToDTO(assetClass));
				financialData.setList(forecastPerClass);
				result.add(financialData);
			}
			Collections.sort(result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private List<FinancialDataElementDTO> getForecastPerClass(List<AssetEntity> assets, int period) throws Exception {
		Map<Long, Map<LocalDate, BigDecimal>> forecastPerAssetMap = getForecastPerAsset(assets, period);
		List<FinancialDataElementDTO> result = new ArrayList<>();
		for(int i = 1; i <= period; i++) {
			FinancialDataElementDTO element = getFinancialDataElement(new ArrayList<>(forecastPerAssetMap.values()), i);
			result.add(element);
		}
		return result;
	}

	private Map<Long, Map<LocalDate, BigDecimal>> getForecastPerAsset(List<AssetEntity> assets, int period) throws Exception {
		Map<Long, Map<LocalDate, BigDecimal>> result = new HashMap<>();
		for(AssetEntity asset : assets) {
			List<FinancialDataEntity> financialDataEntities = this.financialDataRep.findByAssetAndDateLessThanOrderByDateAsc(asset, LocalDate.now());
			Instances instances = this.getInstancesPerAsset(financialDataEntities);
			Map<LocalDate, BigDecimal> valueMap = this.forecast(instances, period);
			result.put(asset.getId(), valueMap);
		}
		return result;
	}

	private FinancialDataElementDTO getFinancialDataElement(List<Map<LocalDate, BigDecimal>> list, int i) {
		LocalDate date = LocalDate.now().plus(Period.ofDays(i));
		BigDecimal value = new BigDecimal(0);
		for(Map<LocalDate, BigDecimal> map : list) {
			/*************************
			 * Avoid negative values *
			 *************************/
			if(map.get(date).doubleValue() < map.get(date).divide(new BigDecimal(100)).doubleValue()) {
				value = value.add(map.get(date).divide(new BigDecimal(100)));
			} else {
				value = value.add(map.get(date));
			}
		}
		value = value.divide(new BigDecimal(1), 4, RoundingMode.HALF_UP);
		FinancialDataElementDTO element = new FinancialDataElementDTO();
		element.setValue(value);
		element.setDate(date.toString());
		return element;
	}

	private Instances getInstancesPerAsset(List<FinancialDataEntity> list) throws ParseException {
		ArrayList<Attribute> attributes = new ArrayList<>();
		Attribute date = new Attribute(DATE, "yyyy-MM-dd");
		Attribute values = new Attribute(VALUES);
		attributes.add(values);
		attributes.add(date);
		Instances result = new Instances("result", attributes, 0);
		for(FinancialDataEntity entity : list) {
			double[] array = new double[result.numAttributes()];
			array[0] = entity.getValue().doubleValue();
			array[1] = result.attribute(DATE).parseDate((entity.getDate().toString()));
			result.add(new DenseInstance(1.0, array));
		}
		return result;
	}

	private Map<LocalDate, BigDecimal> forecast(Instances instances, int period) throws Exception {
		WekaForecaster forecaster = new WekaForecaster();
		forecaster.setFieldsToForecast(VALUES);
		forecaster.setBaseForecaster(new HoltWinters());
		forecaster.getTSLagMaker().setTimeStampField(DATE);
		forecaster.getTSLagMaker().setPeriodicity(TSLagMaker.Periodicity.DAILY);
		forecaster.buildForecaster(instances, System.out);
		forecaster.primeForecaster(instances);
		List<List<NumericPrediction>> forecast = forecaster.forecast(period);
		List<Value> resultList = new ArrayList<>();
		for (int i = 0; i < forecast.size(); i++) {
			List<NumericPrediction> predsAtStep = forecast.get(i);
			LocalDate date = LocalDate.now().plus(Period.ofDays(i + 1));
			Value value = new Value(date, new BigDecimal(predsAtStep.get(0).predicted()));
			resultList.add(value);
		}
		return Mapper.getMapValues(resultList);
	}

}
