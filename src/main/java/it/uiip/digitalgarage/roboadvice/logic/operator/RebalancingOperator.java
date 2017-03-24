package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RebalancingOperator extends AbstractOperator {

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
	public boolean rebalancePortfolio(Map<Long, List<AssetEntity>> mapAssets, Map<Long, FinancialDataEntity> financialDataMap,
									  UserEntity user, List<PortfolioEntity> currentPortfolio, CapitalEntity capital,
									  List<CustomStrategyEntity> strategy) {
		PortfolioDTO portfolio = createPortfolioDTO(user, currentPortfolio, capital);
		Map<Long, CustomStrategyEntity> strategyMap = Mapper.getMapCustomStrategy(strategy);
		boolean toRebalance = false;
		Map<Long, BigDecimal> mapDifferences = new HashMap<>();
		for(PortfolioElementDTO element : portfolio.getList()) {
			BigDecimal difference = element.getPercentage().subtract(strategyMap.get(element.getId()).getPercentage());
			mapDifferences.put(element.getId(), difference);
			if(difference.abs().doubleValue() > 2.0) {
				toRebalance = true;
			}
		}
		if(toRebalance) {
			this.rebalance(mapDifferences);
		}
		return toRebalance;
	}

	public void rebalance(Map<Long, BigDecimal> mapDifferences) {
		for(Long id : mapDifferences.keySet()) {

		}
	}

	private PortfolioDTO createPortfolioDTO(UserEntity user, List<PortfolioEntity> currentPortfolio, CapitalEntity capital) {
		Map<Long, BigDecimal> assetClassMap = new HashMap<>();
		for(PortfolioEntity entity : currentPortfolio) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), this.portfolioRep.sumValuesForAssetClass(entity.getAssetClass(), user, LocalDate.now()).getValue());
			}
		}

		return this.portfolioWrap.wrapToDTO(user, currentPortfolio, capital.getAmount(), assetClassMap);
	}

}
