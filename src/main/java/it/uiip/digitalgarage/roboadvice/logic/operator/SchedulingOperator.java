package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

@Service
public class SchedulingOperator extends AbstractOperator {
	
	@Autowired 
	private UserOperator userOp;
	
	@Autowired
	private QuandlOperator quandlOp;
	
	@Autowired
	private PortfolioOperator portfolioOp;
	
	@Autowired
	private CapitalOperator capitalOp;
	
	@Autowired
	private CustomStrategyOperator customStrategyOp;

	public static int count;
	public static int quandl;

	//TODO: verificare i miglioramenti prestazionali suggeriti nei todo
	@Scheduled(cron = "0 58 0 * * *")
	public void scheduleTask() {
		count = 0;
		quandl = 0;
		Long start = System.currentTimeMillis();
		//quandlOp.updateFinancialDataSet(); TODO uncomment
		Long middle = System.currentTimeMillis();
		List<UserEntity> users = userOp.getAllUsers();
		SchedulingOperator.count++; //TODO remove counting
		for (UserEntity user : users) {
			List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
			SchedulingOperator.count++; //TODO remove counting
			if(currentPortfolio.isEmpty()) {
				List<CustomStrategyEntity> strategy = this.customStrategyRep.findByUserAndActive(user, true);
				SchedulingOperator.count++; //TODO remove counting
				boolean created = portfolioOp.createUserPortfolio(user, strategy);
				if(created) {
					System.out.println("Created portfolio for user: " + user.getId());
				}
				continue;
			}
			if(user.getLastUpdate().isEqual(LocalDate.now())) {
				System.out.println("Skipped computation for user: " + user.getId());
				continue;
			}
			boolean computed = capitalOp.computeCapital(user);
			if(computed) {
				System.out.println("Computed capital for user: " + user.getId());
			}
			//TODO la chiamata getCustomStrategySet potrebbe essere troppo dispendiosa.
			List<CustomStrategyEntity> strategy = this.customStrategyRep.findByUserAndActive(user, true);
			SchedulingOperator.count++; //TODO remove counting
			if(!strategy.isEmpty() && customStrategyOp.getCustomStrategySet(user, 0).size() > 1 &&
					(strategy.get(0).getDate().equals(LocalDate.now()) ||
					 strategy.get(0).getDate().equals(LocalDate.now().minus(Period.ofDays(1))))) {
				SchedulingOperator.count++; //TODO remove counting
				boolean recreated = portfolioOp.createUserPortfolio(user, strategy);
				if(recreated) {
					System.out.println("Re-created portfolio for user: " + user.getId());
				}
				continue;
			}
			//TODO controllare bug nel compute!
			computed = portfolioOp.computeUserPortfolio(user);
			if(computed) {
				System.out.println("Computed portfolio for user: " + user.getId());
			}
		}
		Long end = System.currentTimeMillis();
		System.out.println("Quandl: " + quandl + " queries");
		System.out.println("Scheduling: " + count + " queries");
		System.out.println("Total: " + (count + quandl) + " queries");
		System.out.println("Quandl computation in " + (middle - start) + " ms");
		System.out.println("Scheduling computation in " + (end - middle) + " ms");
		System.out.println("Total computation in " + (end - start) + " ms");
	}

	/************************************************************************************
	 * 								Test Method											*
	 ************************************************************************************/
	@Scheduled(cron = "0 2 20 * * *")
	public void fillDBUser() {
		Long start = System.currentTimeMillis();
		UserEntity user;
		for(int i = 1; i < 10000; i++) {
			user = new UserEntity();
			user.setLastUpdate(LocalDate.now());
			user.setPassword(HashFunction.hashStringSHA256("stress"));
			user.setDate(LocalDate.now());
			user.setEmail(i + "@stress");
			this.userRep.save(user);
			CapitalRequestDTO capital = new CapitalRequestDTO();
			capital.setAmount(new BigDecimal(10).add(new BigDecimal(i)));
			this.capitalOp.addCapital(capital, user);
			CustomStrategyDTO strategy = new CustomStrategyDTO();
			List<AssetClassStrategyDTO> list = new ArrayList<>();
			AssetClassStrategyDTO assetClassStrategyDTO = new AssetClassStrategyDTO();
			AssetClassStrategyDTO assetClassStrategyDTO2 = new AssetClassStrategyDTO();
			AssetClassStrategyDTO assetClassStrategyDTO3 = new AssetClassStrategyDTO();
			AssetClassStrategyDTO assetClassStrategyDTO4 = new AssetClassStrategyDTO();
			assetClassStrategyDTO.setId(new Long(1));
			assetClassStrategyDTO.setName("bonds");
			assetClassStrategyDTO.setPercentage(new BigDecimal(25));
			assetClassStrategyDTO2.setId(new Long(2));
			assetClassStrategyDTO2.setName("forex");
			assetClassStrategyDTO2.setPercentage(new BigDecimal(25));
			assetClassStrategyDTO3.setId(new Long(3));
			assetClassStrategyDTO3.setName("stocks");
			assetClassStrategyDTO3.setPercentage(new BigDecimal(25));
			assetClassStrategyDTO4.setId(new Long(4));
			assetClassStrategyDTO4.setName("commodities");
			assetClassStrategyDTO4.setPercentage(new BigDecimal(25));
			list.add(assetClassStrategyDTO);
			list.add(assetClassStrategyDTO2);
			list.add(assetClassStrategyDTO3);
			list.add(assetClassStrategyDTO4);
			strategy.setList(list);
			this.customStrategyOp.setCustomStrategy(strategy, user);
		}
		Long end = System.currentTimeMillis();
		System.out.println("Fill DB computation in " + (end - start) + " ms");
	}
	 /************************************************************************************/

}
