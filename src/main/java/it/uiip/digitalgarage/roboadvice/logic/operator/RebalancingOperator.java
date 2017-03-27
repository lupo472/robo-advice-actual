package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RebalancingOperator extends AbstractOperator {

	public List<PortfolioEntity> rebalance(UserEntity user, List<PortfolioEntity> currentPortfolio, List<CustomStrategyEntity> strategy,
	  									   Map<Long, FinancialDataEntity> financialDataMap) {
		List<AssetEntity> assets = this.assetRep.findAll();
		Map<Long, List<AssetEntity>> assetsPerClassMap = Mapper.getMapAssets(assets);
		BigDecimal capital = new BigDecimal(0);
		for(PortfolioEntity entity : currentPortfolio) {
			capital = capital.add(entity.getValue());
		}
		PortfolioDTO portfolio = createPortfolioDTO(user, currentPortfolio, capital);
		Map<Long, CustomStrategyEntity> strategyMap = Mapper.getMapCustomStrategy(strategy);
		boolean toRebalance = false;
		Map<Long, BigDecimal> differencePerClassMap = new HashMap<>();
		Map<Long, BigDecimal> capitalPerClassMap = new HashMap<>();
		toRebalance = isToRebalance(capital, portfolio, strategyMap, toRebalance, differencePerClassMap, capitalPerClassMap);
		if(toRebalance) {
			currentPortfolio = this.rebalance(assetsPerClassMap, financialDataMap, currentPortfolio, capital, differencePerClassMap, capitalPerClassMap);
		}
		return currentPortfolio;
	}

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo"}, allEntries = true)
	public boolean rebalancePortfolio(Map<Long, List<AssetEntity>> assetsPerClassMap, Map<Long, FinancialDataEntity> financialDataMap,
									  UserEntity user, List<PortfolioEntity> currentPortfolio, CapitalEntity capital,
									  List<CustomStrategyEntity> strategy) {
		PortfolioDTO portfolio = createPortfolioDTO(user, currentPortfolio, capital.getAmount());
		Map<Long, CustomStrategyEntity> strategyMap = Mapper.getMapCustomStrategy(strategy);
		boolean toRebalance = false;
		Map<Long, BigDecimal> differencePerClassMap = new HashMap<>();
		Map<Long, BigDecimal> capitalPerClassMap = new HashMap<>();
		toRebalance = isToRebalance(capital.getAmount(), portfolio, strategyMap, toRebalance, differencePerClassMap, capitalPerClassMap);
		if(toRebalance) {
			currentPortfolio = this.rebalance(assetsPerClassMap, financialDataMap, currentPortfolio, capital.getAmount(), differencePerClassMap, capitalPerClassMap);
			this.portfolioRep.save(currentPortfolio);
		}
		return toRebalance;
	}

	private boolean isToRebalance(BigDecimal capital, PortfolioDTO portfolio, Map<Long, CustomStrategyEntity> strategyMap, boolean toRebalance, Map<Long, BigDecimal> differencePerClassMap, Map<Long, BigDecimal> capitalPerClassMap) {
		for(PortfolioElementDTO element : portfolio.getList()) {
			BigDecimal differencePerClass = element.getPercentage().subtract(strategyMap.get(element.getId()).getPercentage());
			BigDecimal capitalPerClass = capital.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(element.getPercentage());
			differencePerClassMap.put(element.getId(), differencePerClass);
			capitalPerClassMap.put(element.getId(), capitalPerClass);
			if(!toRebalance && differencePerClass.abs().doubleValue() > 2.0) {
				toRebalance = true;
			}
		}
		return toRebalance;
	}

	private List<PortfolioEntity> rebalance(Map<Long, List<AssetEntity>> assetPerClassMap, Map<Long, FinancialDataEntity> financialDataMap,
						  List<PortfolioEntity> currentPortfolio, BigDecimal capital,
						   Map<Long, BigDecimal> differencePerClassMap, Map<Long, BigDecimal> capitalPerClassMap) {
		Map<Long, PortfolioEntity> portfolioMap = Mapper.getMapPortfolio(currentPortfolio);
		for(Long id : differencePerClassMap.keySet()) {
			BigDecimal capitalPerClass = capitalPerClassMap.get(id);
			BigDecimal capitalDifferencePerClass = this.getCapitalDifferencePerClass(differencePerClassMap.get(id), capital);
			rebalanceAssetClass(assetPerClassMap, financialDataMap, portfolioMap, id, capitalPerClass, capitalDifferencePerClass);
		}
		return currentPortfolio;
	}

	private void rebalanceAssetClass(Map<Long, List<AssetEntity>> assetPerClassMap, Map<Long, FinancialDataEntity> financialDataMap, Map<Long, PortfolioEntity> portfolioMap, Long id, BigDecimal capitalPerClass, BigDecimal capitalDifferencePerClass) {
		for(AssetEntity asset : assetPerClassMap.get(id)) {
			FinancialDataEntity financialData = financialDataMap.get(asset.getId());
			PortfolioEntity portfolio = portfolioMap.get(asset.getId());
			BigDecimal currentUnits = rebalanceAsset(capitalPerClass, asset, financialData, portfolio);
			BigDecimal capitalDifferencePerAsset = capitalDifferencePerClass.divide(new BigDecimal(100.0), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
			BigDecimal unitsDifference = capitalDifferencePerAsset.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
			BigDecimal newUnits = currentUnits.subtract(unitsDifference);
			BigDecimal value = newUnits.multiply(financialData.getValue());
			portfolio.setUnits(newUnits);
			portfolio.setValue(value);
		}
	}

	private BigDecimal rebalanceAsset(BigDecimal capitalPerClass, AssetEntity asset, FinancialDataEntity financialData, PortfolioEntity portfolio) {
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

	private PortfolioDTO createPortfolioDTO(UserEntity user, List<PortfolioEntity> currentPortfolio, BigDecimal capital) {
		Map<Long, BigDecimal> assetClassMap = new HashMap<>();
		for(PortfolioEntity entity : currentPortfolio) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), new BigDecimal(0));
			}
			BigDecimal current = assetClassMap.get(entity.getAssetClass().getId());
			current = current.add(entity.getValue());
			assetClassMap.put(entity.getAssetClass().getId(), current);
		}
		return this.portfolioWrap.wrapToDTO(currentPortfolio, capital, assetClassMap);
	}

}
