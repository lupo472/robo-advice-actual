package it.uiip.digitalgarage.roboadvice.logic.operator;

import org.joda.time.DateTime;
import org.jvnet.hk2.annotations.Service;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.*;
import weka.experiment.InstanceQuery;
import weka.filters.supervised.attribute.TSLagMaker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PredictionOperator extends AbstractOperator {

	private static DateTime getCurrentDateTime(TSLagMaker lm) throws Exception {
		return new DateTime((long) lm.getCurrentTimeStampValue());
	}

	private static DateTime advanceTime(TSLagMaker lm, DateTime dt) {
		return new DateTime((long) lm.advanceSuppliedTimeValue(dt.getMillis()));
	}

	public static void main(String[] args) {
		try {
			String pathToWineData = weka.core.WekaPackageManager.PACKAGES_DIR.toString()
					+ File.separator + "timeseriesForecasting" + File.separator + "sample-data"
					+ File.separator + "prova.arff";

			ArrayList<Attribute> attributes = new ArrayList<>();
			Attribute attribute = new Attribute("date", "yyyy-MM-dd");
			Attribute attribute1 = new Attribute("valori");
			attributes.add(attribute1);
			attributes.add(attribute);
			Instances prova = new Instances("prova", attributes, 0);
			System.out.println(prova.numAttributes());
			for(int i = 0; i < 200; i++) {
				double[] vals = new double[prova.numAttributes()];
				vals[0] = (1.1 + i);
				LocalDate date = LocalDate.now().plus(Period.ofDays(i));
				vals[1] = prova.attribute("date").parseDate((date.toString()));
				prova.add(new DenseInstance(1.0, vals));
			}




			// load the data
//			Instances wine = new Instances(new BufferedReader(new FileReader(pathToWineData)));


			// new forecaster
			WekaForecaster forecaster = new WekaForecaster();

			// set the targets we want to forecast. This method calls
			// setFieldsToLag() on the lag maker object for us
			forecaster.setFieldsToForecast("valori");

			// default underlying classifier is SMOreg (SVM) - we'll use
			// gaussian processes for regression instead
			forecaster.setBaseForecaster(new LinearRegression());

			forecaster.getTSLagMaker().setTimeStampField("date"); // date time stamp
			forecaster.getTSLagMaker().setMinLag(1);
			forecaster.getTSLagMaker().setMaxLag(12); // monthly data

			// add a month of the year indicator field
//			forecaster.getTSLagMaker().setAddMonthOfYear(true);

			// add a quarter of the year indicator field
			//forecaster.getTSLagMaker().setAddQuarterOfYear(true);
			forecaster.getTSLagMaker().setPeriodicity(TSLagMaker.Periodicity.DAILY);

			// build the model
			forecaster.buildForecaster(prova, System.out);

			// prime the forecaster with enough recent historical data
			// to cover up to the maximum lag. In our case, we could just supply
			// the 12 most recent historical instances, as this covers our maximum
			// lag period
			forecaster.primeForecaster(prova);

			// forecast for 12 units (months) beyond the end of the
			// training data
			int nSteps = 30;
			List<List<NumericPrediction>> forecast = forecaster.forecast(nSteps, System.out);

			DateTime currentDt = getCurrentDateTime(forecaster.getTSLagMaker());


			// output the predictions. Outer list is over the steps; inner list is over
			// the targets
			for (int i = 0; i < nSteps; i++) {
				List<NumericPrediction> predsAtStep = forecast.get(i);

				System.out.print(currentDt + ": ");
//
//                for (int j = 0; j < 1; j++) {
				NumericPrediction predForTarget = predsAtStep.get(0);
				System.out.print("" + predForTarget.predicted() + " ");
//                }
				System.out.println();

				// Advance the current date to the next prediction date
				currentDt = advanceTime(forecaster.getTSLagMaker(), currentDt);
			}

			// we can continue to use the trained forecaster for further forecasting
			// by priming with the most recent historical data (as it becomes available).
			// At some stage it becomes prudent to re-build the model using current
			// historical data.

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
