package it.uiip.digitalgarage.roboadvice.logic.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;

@Service
public class SchedulingOperator {
	
	@Autowired
	private FinancialDataRepository FinancialDataRep;
	
	@Autowired
	private AssetRepository assetRep;
	
	@Scheduled(cron = "0 0 12 * * *")
	public void scheduleTask() {
		QuandlOperator quandlOp = new QuandlOperator(FinancialDataRep, assetRep);
		quandlOp.updateFinancialDataSet();
	}

}
