package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;

import it.uiip.digitalgarage.roboadvice.logic.converter.AssetConverter;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class QuandlDBInitializer {
	
	public List<FinancialDataDTO> getData(AssetEntity asset) {
		QuandlSession session = QuandlSession.create();
		
		TabularResult tabularResult = session.getDataSet(
				DataSetRequest.Builder.of(asset.getDataSource())
				.withStartDate(org.threeten.bp.LocalDate.of(2010, 01, 01))
				.withColumn(asset.getRemarksIndex())
				.build());
		
		List<FinancialDataDTO> list = new ArrayList<>();
		for(int i = 0; i < tabularResult.size(); i++) {
			Row row = tabularResult.get(i);
			Double valueDouble = row.getDouble(1);
			BigDecimal value = new BigDecimal(valueDouble);
			String date = row.getString(0);
			FinancialDataDTO financialData = new FinancialDataDTO();
			financialData.setAsset(new AssetConverter().convertToDTO(asset));
			financialData.setDate(date);
			financialData.setValue(value);
			list.add(financialData);
		}
		return list;
	}

}
