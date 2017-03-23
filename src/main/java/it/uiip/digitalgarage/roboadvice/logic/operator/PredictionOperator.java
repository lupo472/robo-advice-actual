package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataElementDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodRequestDTO;
import org.springframework.stereotype.Service;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.HoltWinters;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.*;
import weka.filters.supervised.attribute.TSLagMaker;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionOperator extends AbstractOperator {

	private static final String VALUES = "values";
	private static final String DATE = "date";

	public List<FinancialDataDTO> getPrediction(PeriodRequestDTO period) {
		List<AssetClassEntity> assetClassSet = this.assetClassRep.findAll();
		for(AssetClassEntity assetClass : assetClassSet) {
			try {
				List<FinancialDataElementDTO> predictionPerClass = this.getPredictionPerClass(assetClass, period.getPeriod());
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		return null;
	}

	private List<FinancialDataElementDTO> getPredictionPerClass(AssetClassEntity assetClass, int period) throws Exception {
		List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
		List<String> names = new ArrayList<>();
		for(AssetEntity asset : assets) {
			System.out.println("Asset " + asset.getName());
			List<FinancialDataEntity> financialDataEntities = this.financialDataRep.findByAssetAndDateLessThanOrderByDateAsc(asset, LocalDate.now());
			names.add(asset.getName());
			Instances instances = this.getInstancesPerAsset(financialDataEntities);
			this.forecast(instances, period);
		}
		return null;
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


	private void forecast(Instances instances, int period) throws Exception {
		WekaForecaster forecaster = new WekaForecaster();
		forecaster.setFieldsToForecast(VALUES);
		forecaster.setBaseForecaster(new HoltWinters());
		forecaster.getTSLagMaker().setTimeStampField(DATE);
		forecaster.getTSLagMaker().setPeriodicity(TSLagMaker.Periodicity.DAILY);
		forecaster.buildForecaster(instances, System.out);
		forecaster.primeForecaster(instances);
		List<List<NumericPrediction>> forecast = forecaster.forecast(period);
		for (int i = 0; i < forecast.size(); i++) {
			List<NumericPrediction> predsAtStep = forecast.get(i);
			LocalDate date = LocalDate.now().plus(Period.ofDays(i + 1));
			System.out.print(date.toString() + " : ");
			NumericPrediction predForTarget = predsAtStep.get(0);
			System.out.print("" + predForTarget.predicted() + " ");
			System.out.println();
		}
	}

}
