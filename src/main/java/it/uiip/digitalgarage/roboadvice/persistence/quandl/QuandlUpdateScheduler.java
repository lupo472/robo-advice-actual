package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.TabularResult;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.logic.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

@RestClientTest
public class QuandlUpdateScheduler {

	public void update(AssetEntity asset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -5);
				
		QuandlSession session = QuandlSession.create();

		TabularResult tabularResult = session.getDataSet(
				DataSetRequest.Builder.of(asset.getDataSource())
				.withStartDate(LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH)))
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
	
//	public static void main(String[] args) {
//		QuandlUpdateScheduler.execute();
//		new QuandlUpdateScheduler().execute();
		//List<AssetClassEntity> assetclasses = q.daoRepository.count();
//		for (AssetClassEntity assetClassEntity : assetclasses) {
//			System.out.println(assetClassEntity.getName());
//		}
		
//	}
	
}
