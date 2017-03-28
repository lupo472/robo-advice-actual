package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.persistence.util.User;
import it.uiip.digitalgarage.roboadvice.persistence.util.Value;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * This class manages all the computations for forecasting and demo.
 *
 * @author Cristian Laurini
 */
@Service
public class ForecastingOperator extends AbstractOperator {

	@Autowired
	private RebalancingOperator rebalancingOp;

	private static final String VALUES = "values";
	private static final String DATE = "date";

	/**
	 * This method compute the demo for the logged user for the selected period.
	 *
	 * @param period	PeriodDTO is the number of days for the demo.
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @return			List of PortfolioDTOs as demo or null if the user doesn't have any strategy or capital.
	 */
	@Cacheable("demo")
	public List<PortfolioDTO> getDemo(PeriodDTO period, Authentication auth) {
		UserEntity userEntity = this.userRep.findByEmail(auth.getName());
		List<CustomStrategyEntity> strategyList = this.customStrategyRep.findByUserAndActive(userEntity, true);
		User user = new User();
		user.setUser(userEntity);
		user.setStrategy(strategyList);
		return this.getDemo(user, period);
	}

	/**
	 * This method compute the demo for the logged user for the selected period.
	 *
	 * @param user		User is the user for which you want to get the demo and contains the user's strategy.
	 * @param period	PeriodDTO is the number of days for the demo.
	 * @return			List of PortfolioDTOs as demo or null if the user doesn't have any strategy or capital.
	 */
	@Cacheable("demo")
	public List<PortfolioDTO> getDemo(User user, PeriodDTO period) {
		CapitalEntity capital = this.capitalRep.findByUserAndDate(user.getUser(), user.getUser().getLastUpdate());
		user.setCapital(capital);
		if(user.getStrategy().isEmpty() || user.getCapital() == null) {
			return null;
		}
		try {
			List<PortfolioEntity> currentPortfolio = new ArrayList<>();
			Map<Long, SortedMap<LocalDate, Map<Long, BigDecimal>>>  forecastPerClassMap = new HashMap<>();
			Map<Long, BigDecimal> capitalPerClassMap = new HashMap<>();
			for(CustomStrategyEntity strategy : user.getStrategy()) {
				BigDecimal capitalPerClass = capital.getAmount().divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
				capitalPerClassMap.put(strategy.getAssetClass().getId(), capitalPerClass);
				List<AssetEntity> assets = this.assetRep.findByAssetClass(strategy.getAssetClass());
				SortedMap<LocalDate, Map<Long, BigDecimal>> assetForecastPerDateMap = this.getAssetForecastPerDateMap(assets, period.getPeriod());
				forecastPerClassMap.put(strategy.getAssetClass().getId(), assetForecastPerDateMap);
				List<PortfolioEntity> portfolioListPerClass = createStartingPortfolio(strategy, capitalPerClass, assets, assetForecastPerDateMap);
				currentPortfolio.addAll(portfolioListPerClass);
			}
			user.setPortfolio(currentPortfolio);
			PortfolioDTO portfolio = getPortfolioDTO(capital.getAmount(), currentPortfolio);
			List<PortfolioDTO> result = new ArrayList<>();
			result.add(portfolio);
			Map<Long, PortfolioEntity> portfolioPerAssetMap = Mapper.getMapPortfolio(currentPortfolio);
			boolean goOn = true;
			while(goOn) {
				BigDecimal total = new BigDecimal(0);
				List<FinancialDataEntity> financialDataList = new ArrayList<>();
				for(Long idClass : forecastPerClassMap.keySet()) {
					SortedMap<LocalDate, Map<Long, BigDecimal>> assetForecastPerDateMap = forecastPerClassMap.get(idClass);
					if(assetForecastPerDateMap.keySet().isEmpty()) {
						goOn = false;
						break;
					}
					LocalDate date = assetForecastPerDateMap.firstKey();
					Map<Long, BigDecimal> valuePerAssetMap = assetForecastPerDateMap.get(date);
					assetForecastPerDateMap.remove(date);
					Value value = new Value(date, total);
					total = computePortfolioPerAsset(value, portfolioPerAssetMap, valuePerAssetMap);
					for(Long idAsset : valuePerAssetMap.keySet()) {
						FinancialDataEntity entity = new FinancialDataEntity();
						entity.setDate(date);
						entity.setValue(valuePerAssetMap.get(idAsset));
						AssetEntity asset = new AssetEntity();
						asset.setId(idAsset);
						entity.setAsset(asset);
						financialDataList.add(entity);
					}
				}
				if(!goOn) {
					break;
				}
				Map<Long, FinancialDataEntity> financialDataPerAssetMap = Mapper.getMapFinancialData(financialDataList);
				currentPortfolio = this.rebalancingOp.rebalance(user, financialDataPerAssetMap);
				portfolio = getPortfolioDTO(total, currentPortfolio);
				result.add(portfolio);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private BigDecimal computePortfolioPerAsset(Value value, Map<Long, PortfolioEntity> portfolioPerAssetMap, Map<Long, BigDecimal> valuePerAssetMap) {
		for(Long idAsset : valuePerAssetMap.keySet()) {
			PortfolioEntity entity = portfolioPerAssetMap.get(idAsset);
			BigDecimal units = entity.getUnits();
			BigDecimal newValue = units.multiply(valuePerAssetMap.get(idAsset));
			entity.setValue(newValue);
			entity.setDate(value.getDate());
			value.setValue(value.getValue().add(newValue));
		}
		return value.getValue();
	}

	private PortfolioDTO getPortfolioDTO(BigDecimal total, List<PortfolioEntity> portfolioList) {
		Map<Long, BigDecimal> valuePerAssetClassMap = new HashMap<>();
		for(PortfolioEntity portfolio : portfolioList) {
			if(valuePerAssetClassMap.get(portfolio.getAssetClass().getId()) == null) {
				valuePerAssetClassMap.put(portfolio.getAssetClass().getId(), new BigDecimal(0));
			}
			valuePerAssetClassMap.put(portfolio.getAssetClass().getId(), valuePerAssetClassMap.get(portfolio.getAssetClass().getId()).add(portfolio.getValue()));
		}
		return this.portfolioWrap.wrapToDTO(portfolioList, total, valuePerAssetClassMap);
	}

	private List<PortfolioEntity> createStartingPortfolio(CustomStrategyEntity strategy, BigDecimal capitalPerClass, List<AssetEntity> assets, SortedMap<LocalDate, Map<Long, BigDecimal>> assetForecastPerDateMap) {
		List<PortfolioEntity> portfolioListPerClass = new ArrayList<>();
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
			portfolioListPerClass.add(entity);
		}
		assetForecastPerDateMap.remove(date);
		return portfolioListPerClass;
	}

	private SortedMap<LocalDate, Map<Long, BigDecimal>> getAssetForecastPerDateMap(List<AssetEntity> assets, int period) throws Exception {
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

	/**
	 * This method compute the forecast for the selected period.
	 *
	 * @param period	PeriodDTO is the number of days for the forecast.
	 * @return			List of FinancialData as forecast or null if some problem occurs.
	 */
	@Cacheable("forecast")
	public List<FinancialDataDTO> getForecast(PeriodDTO period) {
		List<AssetClassEntity> assetClassList = this.assetClassRep.findAll();
		List<FinancialDataDTO> result = new ArrayList<>();
		try {
			for(AssetClassEntity assetClass : assetClassList) {
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

	private FinancialDataElementDTO getFinancialDataElement(List<Map<LocalDate, BigDecimal>> valuePerDateMapList, int i) {
		LocalDate date = LocalDate.now().plus(Period.ofDays(i));
		BigDecimal value = new BigDecimal(0);
		for(Map<LocalDate, BigDecimal> map : valuePerDateMapList) {
			/*************************
			 * Avoid negative values *
			 *************************/
			if(map.get(date).doubleValue() < 0) {
				map.put(date, map.get(date.minus(Period.ofDays(1))));
			}
			value = value.add(map.get(date));
		}
		value = value.divide(new BigDecimal(1), 4, RoundingMode.HALF_UP);
		FinancialDataElementDTO element = new FinancialDataElementDTO();
		element.setValue(value);
		element.setDate(date.toString());
		return element;
	}

	private Instances getInstancesPerAsset(List<FinancialDataEntity> financialDataList) throws ParseException {
		ArrayList<Attribute> attributes = new ArrayList<>();
		Attribute date = new Attribute(DATE, "yyyy-MM-dd");
		Attribute values = new Attribute(VALUES);
		attributes.add(values);
		attributes.add(date);
		Instances result = new Instances("result", attributes, 0);
		for(FinancialDataEntity entity : financialDataList) {
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
