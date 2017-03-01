package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

@RestClientTest
public class QuandlUpdateScheduler {

//	public static void execute() {
//		Calendar today = Calendar.getInstance();
//		today.set(Calendar.HOUR_OF_DAY, 2);
//		today.set(Calendar.MINUTE, 0);
//		today.set(Calendar.SECOND, 0);
//		
//		Timer timer = new Timer();
//		timer.schedule(new QuandlUpdateTask(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS));
//	}
	
	public static void main(String[] args) {
//		QuandlUpdateScheduler.execute();
//		new QuandlUpdateScheduler().execute();
		//List<AssetClassEntity> assetclasses = q.daoRepository.count();
//		for (AssetClassEntity assetClassEntity : assetclasses) {
//			System.out.println(assetClassEntity.getName());
//		}
		
	}
	
}
