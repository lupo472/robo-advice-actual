package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

@Service
public class SchedulingOperator {
	
	@Autowired
	private FinancialDataRepository FinancialDataRep;
	
	@Autowired
	private AssetRepository assetRep;
	
	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private PortfolioRepository portfolioRep;
	
	@Scheduled(cron = "0 0 12 * * *")
	public void scheduleTask() {
		QuandlOperator quandlOp = new QuandlOperator(FinancialDataRep, assetRep);
		quandlOp.updateFinancialDataSet();
		UserOperator userOp = new UserOperator(userRep);
		List<UserLoggedDTO> users = userOp.getAllUsers();
		
	}

}
