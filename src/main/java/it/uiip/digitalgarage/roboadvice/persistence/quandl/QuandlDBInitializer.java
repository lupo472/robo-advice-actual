package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import java.math.BigDecimal;
import java.util.List;

import org.threeten.bp.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;

public class QuandlDBInitializer {
	
	public void getData(AssetEntity asset) {
		QuandlSession session = QuandlSession.create();
		
		TabularResult tabularResult = session.getDataSet(
				DataSetRequest.Builder.of(asset.getDataSource())
				.withStartDate(LocalDate.of(2011, 01, 01))
				.withColumn(asset.getRemarksIndex())
				.build());
		
//		for(int i = 0; i < tabularResult.size(); i++) {
//			Row row = tabularResult.get(i);
//			Double value = row.getDouble(1);
//			BigDecimal valueDecimal = new BigDecimal(value);
//			String date = row.getString(0);
//			FinancialData financialData = new FinancialData(asset.getId(), valueDecimal, date);
//			try {
//				new DAOFinancialData().insertFinancialData(financialData);
//			} catch (DAOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
				
		System.out.println(tabularResult.toPrettyPrintedString());
	}

}
