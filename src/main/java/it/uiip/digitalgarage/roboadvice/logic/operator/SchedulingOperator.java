package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
	private RebalancingOperator rebalancingOp;

	@Scheduled(cron = "0 0 10 * * *")
	public void scheduleTask() {
		Long start = System.currentTimeMillis();
		quandlOp.updateFinancialDataSet();
		Long middle = System.currentTimeMillis();
		List<UserEntity> users = userOp.getAllUsers();
		List<AssetEntity> assets = this.assetRep.findAll();
		Map<Long, List<AssetEntity>> mapAssets = Mapper.getMapAssets(assets);
		List<FinancialDataEntity> list = new ArrayList<>();
		for(AssetEntity asset : assets) {
			list.add(financialDataRep.findByAssetAndDate(asset, asset.getLastUpdate()));
		}
		Map<Long, FinancialDataEntity> financialDataMap = Mapper.getMapFinancialData(list);
		userComputation(users, mapAssets, financialDataMap);
		Long end = System.currentTimeMillis();
		System.out.println("Quandl computation in " + (middle - start) + " ms");
		System.out.println("Scheduling computation in " + (end - middle) + " ms");
		System.out.println("Total computation in " + (end - start) + " ms");
	}

	private void userComputation(List<UserEntity> users, Map<Long, List<AssetEntity>> mapAssets, Map<Long, FinancialDataEntity> financialDataMap) {
		for (UserEntity user : users) {
			System.out.println("User: " + user.getId());
			List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
			if(currentPortfolio.isEmpty()) {
				List<CustomStrategyEntity> strategy = this.customStrategyRep.findByUserAndActive(user, true);
				CapitalEntity capitalEntity = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
				boolean created = portfolioOp.createUserPortfolio(user, strategy, capitalEntity, mapAssets, financialDataMap);
				if(created) {
					System.out.println("Created portfolio for user: " + user.getId());
					user.setLastUpdate(LocalDate.now());
					CapitalEntity capital = new CapitalEntity();
					capital.setAmount(capitalEntity.getAmount());
					capital.setUser(capitalEntity.getUser());
					capital.setDate(LocalDate.now());
					capitalRep.save(capital);
					userRep.save(user);
				}
				continue;
			}
			if(user.getLastUpdate().isEqual(LocalDate.now())) {
				System.out.println("Skipped computation for user: " + user.getId());
				continue;
			}
			CapitalEntity capital = capitalOp.computeCapital(user, financialDataMap, currentPortfolio);
			if(capital != null) {
				System.out.println("Computed capital for user: " + user.getId());
			}
			List<CustomStrategyEntity> strategy = this.customStrategyRep.findByUserAndActive(user, true);
			if(!strategy.isEmpty() &&
					(strategy.get(0).getDate().equals(LocalDate.now()) ||
					 strategy.get(0).getDate().equals(LocalDate.now().minus(Period.ofDays(1))))) {
				boolean recreated = portfolioOp.createUserPortfolio(user, strategy, capital, mapAssets, financialDataMap);
				if(recreated) {
					System.out.println("Re-created portfolio for user: " + user.getId());
				}
				continue;
			}
			currentPortfolio = portfolioOp.computeUserPortfolio(user, currentPortfolio, financialDataMap);
			if(currentPortfolio == null) {
				System.out.println("There was an error for user: " + user.getId());
				continue;
			}
			System.out.println("Computed portfolio for user: " + user.getId());
			boolean rebalanced = this.rebalancingOp.rebalancePortfolio(mapAssets, financialDataMap, user, currentPortfolio, capital, strategy);
			if(rebalanced) {
				System.out.println("Re-balanced portfolio for user: " + user.getId());
			}
		}
	}
}
