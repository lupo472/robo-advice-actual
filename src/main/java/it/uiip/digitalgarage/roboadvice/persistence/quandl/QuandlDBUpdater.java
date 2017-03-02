package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import java.time.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.logic.entity.FinancialDataEntity;

@RestClientTest
public class QuandlDBUpdater {

	public List<FinancialDataEntity> getData(AssetEntity asset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -5);
				
		QuandlSession session = QuandlSession.create();

		TabularResult tabularResult = session.getDataSet(
				DataSetRequest.Builder.of(asset.getDataSource())
				.withStartDate(org.threeten.bp.LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH)))
				.withColumn(asset.getRemarksIndex())
				.build());
		
		List<FinancialDataEntity> list = new ArrayList<>();
		for(int i = 0; i < tabularResult.size(); i++) {
			Row row = tabularResult.get(i);
			Double valueDouble = row.getDouble(1);
			BigDecimal value = new BigDecimal(valueDouble);
			String dateString = row.getString(0);
			LocalDate date = LocalDate.parse(dateString);
			FinancialDataEntity financialData = new FinancialDataEntity();
			financialData.setAsset(asset);
			financialData.setDate(date);
			financialData.setValue(value);
			list.add(financialData);
		}
		return list;
	}
	
}
