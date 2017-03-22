package it.uiip.digitalgarage.roboadvice.test;

import org.joda.time.DateTime;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;
import weka.filters.supervised.attribute.TSLagMaker;

import java.io.*;
import java.util.List;

public class TestForecast {

    private static DateTime getCurrentDateTime(TSLagMaker lm) throws Exception {
        return new DateTime((long) lm.getCurrentTimeStampValue());
    }

    private static DateTime advanceTime(TSLagMaker lm, DateTime dt) {
        return new DateTime((long) lm.advanceSuppliedTimeValue(dt.getMillis()));
    }

    public static void main(String[] args) {
        try {
            // path to the Australian wine data included with the time series forecasting
            // package
            String pathToWineData = weka.core.WekaPackageManager.PACKAGES_DIR.toString()
                    + File.separator + "timeseriesForecasting" + File.separator + "sample-data"
                    + File.separator + "prova.arff";

//   ArrayList<Attribute> attributi = new ArrayList<>();
//   Attribute data = new Attribute("data");
//   Attribute valore = new Attribute("valori");
//   attributi.add(data);
//   attributi.add(valore);
//
//   Instances prova = new Instances("prova", attributi, 0);
//   for(int i = 0; i < 800; i++) {
//    Instance instance = new DenseInstance(2);
//    instance.setValue(data, 3);
//    instance.setValue(valore, (1.1+i));
//    prova.add(instance);
//   }

            // load the wine data
            Instances wine = new Instances(new BufferedReader(new FileReader(pathToWineData)));
//   System.out.println("Size wine: " + wine.size()); //TODO
//   System.out.println("Size prova: " + prova.size()); //TODO
            // new forecaster
            WekaForecaster forecaster = new WekaForecaster();

            // set the targets we want to forecast. This method calls
            // setFieldsToLag() on the lag maker object for us
            //forecaster.setFieldsToForecast("Fortified,Dry-white");
            forecaster.setFieldsToForecast("valori");

            // default underlying classifier is SMOreg (SVM) - we'll use
            // gaussian processes for regression instead
            forecaster.setBaseForecaster(new LinearRegression());

            forecaster.getTSLagMaker().setTimeStampField("Date"); // date time stamp
            //forecaster.getTSLagMaker().setMinLag(1);
            //forecaster.getTSLagMaker().setMaxLag(12); // monthly data

            // add a month of the year indicator field
            forecaster.getTSLagMaker().setAddMonthOfYear(true);

            // add a quarter of the year indicator field
            //forecaster.getTSLagMaker().setAddQuarterOfYear(true);
            forecaster.getTSLagMaker().setPeriodicity(TSLagMaker.Periodicity.MONTHLY);

            // build the model
            forecaster.buildForecaster(wine, System.out);

            // prime the forecaster with enough recent historical data
            // to cover up to the maximum lag. In our case, we could just supply
            // the 12 most recent historical instances, as this covers our maximum
            // lag period
            forecaster.primeForecaster(wine);

            // forecast for 12 units (months) beyond the end of the
            // training data
            int nSteps = 12;
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



