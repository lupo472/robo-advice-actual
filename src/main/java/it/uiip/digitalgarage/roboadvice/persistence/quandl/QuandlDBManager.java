package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;

/**
 * This class contains the the methods to get the financial data from Quandl
 *
 * @author Cristian Laurini
 */
public class QuandlDBManager {

	/**
	 * This method allows to get the financial data starting from 2010-01-01.
	 *
	 * @param asset		AssentEntity for wich you want to retrieve data.
	 * @return			List of FinancialDataEntity that contains the data for the requested asset.
	 */
	public List<FinancialDataEntity> getDataInitialization(AssetEntity asset) {
		QuandlSession session = QuandlSession.create("fvEjoT6QAMxEmSAp-9wZ");

		TabularResult tabularResult = session.getDataSet(
				DataSetRequest.Builder.of(asset.getDataSource())
						.withStartDate(org.threeten.bp.LocalDate.of(2010, 01, 01))
						.withColumn(asset.getRemarksIndex())
						.build());

		return this.getFinancialDataEntitySet(asset, tabularResult);
	}

	/**
	 * This method allows to get the financial of the last 5 days
	 *
	 * @param asset		AssentEntity for wich you want to retrieve data.
	 * @return			List of FinancialDataEntity that contains the data for the requested asset.
	 */
	public List<FinancialDataEntity> getDataUpdate(AssetEntity asset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -5);
				
		QuandlSession session = QuandlSession.create("fvEjoT6QAMxEmSAp-9wZ");

		TabularResult tabularResult = session.getDataSet(
				DataSetRequest.Builder.of(asset.getDataSource())
				.withStartDate(org.threeten.bp.LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH)))
				.withColumn(asset.getRemarksIndex())
				.build());

		return this.getFinancialDataEntitySet(asset, tabularResult);
	}

	private List<FinancialDataEntity> getFinancialDataEntitySet(AssetEntity asset, TabularResult tabularResult) {
		List<FinancialDataEntity> result = new ArrayList<>();
		for(int i = 0; i < tabularResult.size(); i++) {
			Row row = tabularResult.get(i);
			Double valueDouble = row.getDouble(1);
			BigDecimal value = new BigDecimal(valueDouble);
			String date = row.getString(0);
			FinancialDataEntity financialData = new FinancialDataEntity();
			financialData.setAsset(asset);
			financialData.setDate(LocalDate.parse(date));
			financialData.setValue(value);
			result.add(financialData);
		}
		return result;
	}

}
