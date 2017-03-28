package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.persistence.util.User;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains methods to re-balance the Portfolios.
 *
 * @author Cristian Laurini
 */
@Service
public class RebalancingOperator extends AbstractOperator {

	/**
	 * This method re-balances a portfolio.
	 *
	 * @param user						User is the user for which you want to re-balance the portfolio
	 *                                  and contains the current portfolio and the active strategy.
	 * @param financialDataPerAssetMap	Map with asset id as key and a FinancialDataEntity as value.
	 * @return							List of PortfolioEntities that represents the re-balanced portfolio.
	 */
	public List<PortfolioEntity> rebalance(User user, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
		List<AssetEntity> assets = this.assetRep.findAll();
		Map<Long, List<AssetEntity>> assetsPerClassMap = Mapper.getMapAssets(assets);
		BigDecimal amount = new BigDecimal(0);
		for(PortfolioEntity entity : user.getPortfolio()) {
			amount = amount.add(entity.getValue());
		}
		CapitalEntity capital = new CapitalEntity();
		capital.setAmount(amount);
		user.setCapital(capital);
		PortfolioDTO portfolio = createPortfolioDTO(user);
		Map<Long, CustomStrategyEntity> strategyPerAssetMap = Mapper.getMapCustomStrategy(user.getStrategy());
		boolean toRebalance = false;
		Map<Long, BigDecimal> differencePerClassMap = new HashMap<>();
		Map<Long, BigDecimal> capitalPerClassMap = new HashMap<>();
		toRebalance = isToRebalance(toRebalance, user.getCapital().getAmount(), portfolio, strategyPerAssetMap, differencePerClassMap, capitalPerClassMap);
		if(toRebalance) {
			user.setPortfolio(this.rebalance(user, assetsPerClassMap, financialDataPerAssetMap, differencePerClassMap, capitalPerClassMap));
		}
		return user.getPortfolio();
	}

	/**
	 * This method re-balances a portfolio.
	 *
	 * @param user						User is the user for which you want to re-balance the portfolio
	 *                                  and contains the current portfolio and the active strategy.
	 * @param assetsPerClassMap			Map with asset class id as key and a List of assets as value.
	 * @param financialDataPerAssetMap	Map with asset id as key and a FinancialDataEntity as value.
	 * @return							Boolean that is true if the portfolio has been re-balanced, false instead.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public boolean rebalancePortfolio(User user, Map<Long, List<AssetEntity>> assetsPerClassMap, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
		PortfolioDTO portfolio = createPortfolioDTO(user);
		Map<Long, CustomStrategyEntity> strategyPerClassMap = Mapper.getMapCustomStrategy(user.getStrategy());
		boolean toRebalance = false;
		Map<Long, BigDecimal> differencePerClassMap = new HashMap<>();
		Map<Long, BigDecimal> capitalPerClassMap = new HashMap<>();
		toRebalance = isToRebalance(toRebalance, user.getCapital().getAmount(), portfolio, strategyPerClassMap, differencePerClassMap, capitalPerClassMap);
		if(toRebalance) {
			user.setPortfolio(this.rebalance(user, assetsPerClassMap, financialDataPerAssetMap, differencePerClassMap, capitalPerClassMap));
			this.portfolioRep.save(user.getPortfolio());
		}
		return toRebalance;
	}

	private boolean isToRebalance(boolean toRebalance, BigDecimal amount, PortfolioDTO portfolio, Map<Long, CustomStrategyEntity> strategyPerClassMap, Map<Long, BigDecimal> differencePerClassMap, Map<Long, BigDecimal> capitalPerClassMap) {
		for(PortfolioElementDTO element : portfolio.getList()) {
			BigDecimal differencePerClass = element.getPercentage().subtract(strategyPerClassMap.get(element.getId()).getPercentage());
			BigDecimal capitalPerClass = amount.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(element.getPercentage());
			differencePerClassMap.put(element.getId(), differencePerClass);
			capitalPerClassMap.put(element.getId(), capitalPerClass);
			if(!toRebalance && differencePerClass.abs().doubleValue() > 1.0) {
				toRebalance = true;
			}
		}
		return toRebalance;
	}

	private List<PortfolioEntity> rebalance(User user, Map<Long, List<AssetEntity>> assetsPerClassMap, Map<Long, FinancialDataEntity> financialDataPerAssetMap,
						   					Map<Long, BigDecimal> differencePerClassMap, Map<Long, BigDecimal> capitalPerClassMap) {
		Map<Long, PortfolioEntity> portfolioPerAssetMap = Mapper.getMapPortfolio(user.getPortfolio());
		for(Long assetClassId : differencePerClassMap.keySet()) {
			BigDecimal capitalPerClass = capitalPerClassMap.get(assetClassId);
			BigDecimal capitalDifferencePerClass = this.getCapitalDifferencePerClass(differencePerClassMap.get(assetClassId), user.getCapital().getAmount());
			this.rebalanceAssetClass(assetClassId, assetsPerClassMap, financialDataPerAssetMap, portfolioPerAssetMap, capitalPerClass, capitalDifferencePerClass);
		}
		return user.getPortfolio();
	}

	private void rebalanceAssetClass(Long assetClassId, Map<Long, List<AssetEntity>> assetPerClassMap, Map<Long, FinancialDataEntity> financialDataAssetMap, Map<Long, PortfolioEntity> portfolioPerAssetMap, BigDecimal capitalPerClass, BigDecimal capitalDifferencePerClass) {
		for(AssetEntity asset : assetPerClassMap.get(assetClassId)) {
			FinancialDataEntity financialData = financialDataAssetMap.get(asset.getId());
			PortfolioEntity portfolio = portfolioPerAssetMap.get(asset.getId());
			BigDecimal currentUnits = this.rebalanceAsset(asset, capitalPerClass, financialData, portfolio);
			BigDecimal capitalDifferencePerAsset = capitalDifferencePerClass.divide(new BigDecimal(100.0), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
			BigDecimal unitsDifference = capitalDifferencePerAsset.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
			BigDecimal newUnits = currentUnits.subtract(unitsDifference);
			BigDecimal value = newUnits.multiply(financialData.getValue());
			portfolio.setUnits(newUnits);
			portfolio.setValue(value);
		}
	}

	private BigDecimal rebalanceAsset(AssetEntity asset, BigDecimal capitalPerClass, FinancialDataEntity financialData, PortfolioEntity portfolio) {
		BigDecimal currentUnits = portfolio.getUnits();
		BigDecimal currentValue = currentUnits.multiply(financialData.getValue());
		BigDecimal capitalPerAsset = capitalPerClass.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
		BigDecimal capitalDifferencePerAsset = capitalPerAsset.subtract(currentValue);
		BigDecimal newUnits = capitalDifferencePerAsset.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
		currentUnits = currentUnits.add(newUnits);
		return currentUnits;
	}

	private BigDecimal getCapitalDifferencePerClass(BigDecimal difference, BigDecimal totalCapital) {
		BigDecimal result = totalCapital.divide(new BigDecimal(100.0), 8, RoundingMode.HALF_UP).multiply(difference);
		return result;
	}

	private PortfolioDTO createPortfolioDTO(User user) {
		BigDecimal capital = user.getCapital().getAmount();
		Map<Long, BigDecimal> assetClassMap = new HashMap<>();
		for(PortfolioEntity portfolio : user.getPortfolio()) {
			if(assetClassMap.get(portfolio.getAssetClass().getId()) == null) {
				assetClassMap.put(portfolio.getAssetClass().getId(), new BigDecimal(0));
			}
			BigDecimal current = assetClassMap.get(portfolio.getAssetClass().getId());
			current = current.add(portfolio.getValue());
			assetClassMap.put(portfolio.getAssetClass().getId(), current);
		}
		return this.portfolioWrap.wrapToDTO(user.getPortfolio(), capital, assetClassMap);
	}

}
