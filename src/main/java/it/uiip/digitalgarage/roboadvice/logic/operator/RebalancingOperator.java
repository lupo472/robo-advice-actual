package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RebalancingOperator extends AbstractOperator {

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
	public boolean rebalancePortfolio(UserEntity user, List<CustomStrategyEntity> strategyEntity, CapitalEntity capital,
									  Map<Long, List<AssetEntity>> mapAssets, Map<Long, FinancialDataEntity> mapFD) {


		return true;
	}

}
