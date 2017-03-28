package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * This class manages all the nightly computation
 *
 * @author Cristian Laurini
 */
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

	/**
	 * This method is computed every day at the same time.
	 * It updates the financial data, then, for each user in the database,
	 * it creates the portfolio for the new users, while for the others,
	 * it computes the new capital and then it recreates the portfolio for the
	 * users that have changed their strategy in the last day or computes the
	 * portfolio with the new financial data and re-balances if necessary
	 * for the others.
	 */
	@Scheduled(cron = "0 0 10 * * *")
	public void scheduleTask() {
		Long start = System.currentTimeMillis();
		quandlOp.updateFinancialDataSet();
		Long quandl = System.currentTimeMillis();
		List<UserEntity> users = userOp.getAllUsers();
		List<AssetEntity> assets = this.assetRep.findAll();
		Map<Long, List<AssetEntity>> assetsMap = Mapper.getMapAssets(assets);
		List<FinancialDataEntity> financialDataEntities = new ArrayList<>();
		for(AssetEntity asset : assets) {
			financialDataEntities.add(financialDataRep.findByAssetAndDate(asset, asset.getLastUpdate()));
		}
		Map<Long, FinancialDataEntity> financialDataPerAssetMap = Mapper.getMapFinancialData(financialDataEntities);
		this.userComputation(users, assetsMap, financialDataPerAssetMap);
		Long end = System.currentTimeMillis();
		/*Time checking*/
		System.out.println("Quandl computation in " + (quandl - start) + " ms");
		System.out.println("Scheduling computation in " + (end - quandl) + " ms");
		System.out.println("Total computation in " + (end - start) + " ms");
	}

	private void userComputation(List<UserEntity> users, Map<Long, List<AssetEntity>> assetsMap, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
		for (UserEntity user : users) {
			List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
			if(currentPortfolio.isEmpty()) {
				List<CustomStrategyEntity> strategy = this.customStrategyRep.findByUserAndActive(user, true);
				CapitalEntity currentCapital = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
				boolean created = portfolioOp.createUserPortfolio(user, strategy, currentCapital, assetsMap, financialDataPerAssetMap);
				if(created) {
					System.out.println("Created portfolio for user: " + user.getId());
					user.setLastUpdate(LocalDate.now());
					CapitalEntity capital = new CapitalEntity();
					capital.setAmount(currentCapital.getAmount());
					capital.setUser(currentCapital.getUser());
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
			CapitalEntity capital = capitalOp.computeCapital(user, financialDataPerAssetMap, currentPortfolio);
			if(capital != null) {
				System.out.println("Computed capital for user: " + user.getId());
			}
			List<CustomStrategyEntity> strategy = this.customStrategyRep.findByUserAndActive(user, true);
			if(!strategy.isEmpty() &&
					(strategy.get(0).getDate().equals(LocalDate.now()) ||
					 strategy.get(0).getDate().equals(LocalDate.now().minus(Period.ofDays(1))))) {
				boolean recreated = portfolioOp.createUserPortfolio(user, strategy, capital, assetsMap, financialDataPerAssetMap);
				if(recreated) {
					System.out.println("Re-created portfolio for user: " + user.getId());
				}
				continue;
			}
			currentPortfolio = portfolioOp.computeUserPortfolio(user, currentPortfolio, financialDataPerAssetMap);
			if(currentPortfolio == null) {
				System.out.println("There was an error for user: " + user.getId());
				continue;
			}
			System.out.println("Computed portfolio for user: " + user.getId());
			boolean rebalanced = this.rebalancingOp.rebalancePortfolio(assetsMap, financialDataPerAssetMap, user, currentPortfolio, capital, strategy);
			if(rebalanced) {
				System.out.println("Re-balanced portfolio for user: " + user.getId());
			}
		}
	}

}
