package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.time.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class QuandlDBUpdater {

	public List<FinancialDataDTO> getData(AssetEntity asset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -5);
				
		QuandlSession session = QuandlSession.create();

		TabularResult tabularResult = session.getDataSet(
				DataSetRequest.Builder.of(asset.getDataSource())
				.withStartDate(org.threeten.bp.LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH)))
				.withColumn(asset.getRemarksIndex())
				.build());
		
		List<FinancialDataDTO> list = new ArrayList<>();
		for(int i = 0; i < tabularResult.size(); i++) {
			Row row = tabularResult.get(i);
			Double valueDouble = row.getDouble(1);
			BigDecimal value = new BigDecimal(valueDouble);
			String dateString = row.getString(0);
			LocalDate date = LocalDate.parse(dateString);
			FinancialDataDTO financialData = new FinancialDataDTO();
			financialData.setAsset(asset);
			financialData.setDate(date);
			financialData.setValue(value);
			list.add(financialData);
		}
		return list;
	}
	
}
