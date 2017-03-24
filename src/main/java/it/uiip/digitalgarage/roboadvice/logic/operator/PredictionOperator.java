package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.persistence.util.Value;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataElementDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodRequestDTO;
import org.springframework.cache.annotation.Cacheable;
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
public class PredictionOperator extends AbstractOperator {

	private static final String VALUES = "values";
	private static final String DATE = "date";

	@Cacheable("forecast")
	public List<FinancialDataDTO> getForecast(PeriodRequestDTO period) {
		List<AssetClassEntity> assetClassSet = this.assetClassRep.findAll();
		List<FinancialDataDTO> result = new ArrayList<>();
		try {
			for(AssetClassEntity assetClass : assetClassSet) {
				List<FinancialDataElementDTO> predictionPerClass = this.getPredictionPerClass(assetClass, period.getPeriod());
				FinancialDataDTO financialData = new FinancialDataDTO();
				financialData.setAssetClass(this.assetClassConv.convertToDTO(assetClass));
				financialData.setList(predictionPerClass);
				result.add(financialData);
			}
			Collections.sort(result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private List<FinancialDataElementDTO> getPredictionPerClass(AssetClassEntity assetClass, int period) throws Exception {
		List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
		List<Map<LocalDate, BigDecimal>> list = new ArrayList<>();
		for(AssetEntity asset : assets) {
			List<FinancialDataEntity> financialDataEntities = this.financialDataRep.findByAssetAndDateLessThanOrderByDateAsc(asset, LocalDate.now());
			Instances instances = this.getInstancesPerAsset(financialDataEntities);
			Map<LocalDate, BigDecimal> valueMap = this.forecast(instances, period);
			list.add(valueMap);
		}
		List<FinancialDataElementDTO> result = new ArrayList<>();
		for(int i = 1; i <= period; i++) {
			FinancialDataElementDTO element = getFinancialDataElement(list, i);
			result.add(element);
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
