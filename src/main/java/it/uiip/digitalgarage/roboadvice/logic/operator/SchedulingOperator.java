package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

@Service
public class SchedulingOperator {
	
	@Autowired
	private FinancialDataRepository financialDataRep;
	
	@Autowired
	private AssetRepository assetRep;
	
	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private PortfolioRepository portfolioRep;
	
	@Autowired
	private CapitalRepository capitalRep;
	
	@Autowired
	private CustomStrategyRepository customStrategyRep;
	
	//@Scheduled(cron = "0 * * * * *")
	public void scheduleTask() {
		QuandlOperator quandlOp = new QuandlOperator(this.financialDataRep, this.assetRep);
		UserOperator userOp = new UserOperator(this.userRep);
		PortfolioOperator portfolioOp = new PortfolioOperator(this.portfolioRep, this.capitalRep, this.customStrategyRep, assetRep, financialDataRep, userRep);
		CustomStrategyOperator customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);
		CapitalOperator capitalOp = new CapitalOperator(this.capitalRep, this.portfolioRep);
		
		quandlOp.updateFinancialDataSet();
		List<UserLoggedDTO> users = userOp.getAllUsers();
		for (UserLoggedDTO user : users) {
			//Operazioni
			PortfolioDTO currentPortfolio = portfolioOp.getUserCurrentPortfolio(user);
			if(currentPortfolio == null) {
				boolean created = portfolioOp.createUserPortfolio(user);
				if(created) {
					System.out.println("Created portfolio for user: " + user.getId());
				}
				continue;
			}
			
			
		}
//		for (UserLoggedDTO user : users) {
//			CustomStrategyResponseDTO strategy = customStrategyOp.getActiveUserCustomStrategy(user);
//			if(strategy != null && (strategy.getDate().equals(LocalDate.now().toString()) || 
//					strategy.getDate().equals(LocalDate.now().minus(Period.ofDays(1)).toString()))) {
//				boolean recreated = portfolioOp.recreatePortfolio(user);
//				if(recreated) {
//					System.out.println("Re-created portfolio for user: " + user.getId());
//				}
//			} else {
//				PortfolioDTO currentPortfolio = portfolioOp.getUserCurrentPortfolio(user);
//				if(currentPortfolio == null) {
//					boolean created = portfolioOp.createUserPortfolio(user);
//					if(created) {
//						System.out.println("Created portfolio for user: " + user.getId());
//					}
//				} else {
//					boolean computed = portfolioOp.computeUserPortfolio(user);
//					if(computed) {
//						System.out.println("Computed portfolio for user: " + user.getId());
//					}
//				}
//			}
//		}
	}

}
