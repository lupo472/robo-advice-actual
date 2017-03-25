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

	//TODO working on rebalancing
	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
	public boolean rebalancePortfolio(Map<Long, List<AssetEntity>> mapAssets, Map<Long, FinancialDataEntity> financialDataMap,
									  UserEntity user, List<PortfolioEntity> currentPortfolio, CapitalEntity capital,
									  List<CustomStrategyEntity> strategy) {
		PortfolioDTO portfolio = createPortfolioDTO(user, currentPortfolio, capital);
		Map<Long, CustomStrategyEntity> strategyMap = Mapper.getMapCustomStrategy(strategy);
		boolean toRebalance = false;
		Map<Long, BigDecimal> mapDifferences = new HashMap<>();
		Map<Long, BigDecimal> mapCapitalPerClass = new HashMap<>();
		for(PortfolioElementDTO element : portfolio.getList()) {
			BigDecimal difference = element.getPercentage().subtract(strategyMap.get(element.getId()).getPercentage());
			BigDecimal capitalPerClass = capital.getAmount().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP).multiply(element.getPercentage());
			mapDifferences.put(element.getId(), difference);
			mapCapitalPerClass.put(element.getId(), capitalPerClass);
			System.out.println("Capital per class: " + capitalPerClass); //TODO
			System.out.println("Differenza " + element.getName() + " " + difference); //TODO
			if(!toRebalance && difference.abs().doubleValue() > 2.0) {
				toRebalance = true;
			}
		}
		if(toRebalance) {
			this.rebalance(mapAssets, financialDataMap, currentPortfolio, capital, mapDifferences, mapCapitalPerClass);
		}
		return toRebalance;
	}

	private void rebalance(Map<Long, List<AssetEntity>> mapAssets, Map<Long, FinancialDataEntity> financialDataMap,
						  List<PortfolioEntity> currentPortfolio, CapitalEntity capital,
						   Map<Long, BigDecimal> mapDifferences, Map<Long, BigDecimal> mapCapitalPerClass) {
		Map<Long, PortfolioEntity> portfolioMap = Mapper.getMapPortfolio(currentPortfolio);
		for(Long id : mapDifferences.keySet()) {
			BigDecimal capitalDifference = this.getDifferenceForAssetClass(mapDifferences.get(id), capital.getAmount());
			System.out.println("Capital Difference per: " + id + " è " + capitalDifference); //TODO
			for(AssetEntity asset : mapAssets.get(id)) {
				BigDecimal assetDifference = capitalDifference.divide(new BigDecimal(100.0), 4, RoundingMode.HALF_UP).multiply(asset.getPercentage());
				System.out.println("Asset difference per " + asset.getName() + " è " + assetDifference); //TODO
				BigDecimal currentUnit = portfolioMap.get(asset.getId()).getUnits();
				FinancialDataEntity financialData = financialDataMap.get(asset.getId());
				System.out.println("Avevo: " + currentUnit); //TODO
				System.out.println("Valore: " + currentUnit.multiply(financialData.getValue())); //TODO
				BigDecimal newUnits = assetDifference.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
				BigDecimal units = currentUnit.subtract(newUnits);
				BigDecimal value = units.multiply(financialData.getValue());
				System.out.println("Ho: " + units); //TODO
				System.out.println("Valore: " + value); //TODO
				portfolioMap.get(asset.getId()).setUnits(units);
				portfolioMap.get(asset.getId()).setValue(value);
				System.out.println("--------"); //TODO
			}
			System.out.println("--------\n");  //TODO
		}
		this.portfolioRep.save(currentPortfolio);

	}

	private BigDecimal getDifferenceForAssetClass(BigDecimal difference, BigDecimal totalCapital) {
		BigDecimal result = totalCapital.divide(new BigDecimal(100.0), 4, RoundingMode.HALF_UP).multiply(difference);
		return result;
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
