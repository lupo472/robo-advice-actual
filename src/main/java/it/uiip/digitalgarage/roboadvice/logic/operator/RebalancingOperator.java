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
		Map<Long, BigDecimal> assetClassMap = new HashMap<>();
		for(PortfolioEntity entity : currentPortfolio) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), this.portfolioRep.sumValuesForAssetClass(entity.getAssetClass(), user, LocalDate.now()).getValue());
			}
		}

		PortfolioDTO portfolio = this.portfolioWrap.wrapToDTO(user, currentPortfolio, capital.getAmount(), assetClassMap);
		Map<Long, CustomStrategyEntity> strategyMap = Mapper.getMapCustomStrategy(strategy);
		for(PortfolioElementDTO element : portfolio.getList()) {
			System.out.println("Differenza: " + element.getPercentage().subtract(strategyMap.get(element.getId()).getPercentage()).abs().doubleValue());
			if(element.getPercentage().subtract(strategyMap.get(element.getId()).getPercentage()).abs().doubleValue() > 2.0) {
//				boolean rebalanced = this.rebalancingOp.rebalancePortfolio(user, strategy, capital, mapAssets, financialDataMap);
//				if(rebalanced) {
//					System.out.println("Re-balanced portfolio for user: " + user.getId());
//					break;
//				}
			}
		}

		return true;
	}

}
