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
	public boolean rebalancePortfolio(Map<Long, List<AssetEntity>> assetsPerClassMap, Map<Long, FinancialDataEntity> financialDataMap,
									  UserEntity user, List<PortfolioEntity> currentPortfolio, CapitalEntity capital,
									  List<CustomStrategyEntity> strategy) {
		PortfolioDTO portfolio = createPortfolioDTO(user, currentPortfolio, capital);
		Map<Long, CustomStrategyEntity> strategyMap = Mapper.getMapCustomStrategy(strategy);
		boolean toRebalance = false;
		Map<Long, BigDecimal> differencePerClassMap = new HashMap<>();
		Map<Long, BigDecimal> capitalPerClassMap = new HashMap<>();
		for(PortfolioElementDTO element : portfolio.getList()) {
			BigDecimal differencePerClass = element.getPercentage().subtract(strategyMap.get(element.getId()).getPercentage());
			BigDecimal capitalPerClass = capital.getAmount().divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(element.getPercentage());
			differencePerClassMap.put(element.getId(), differencePerClass);
			capitalPerClassMap.put(element.getId(), capitalPerClass);
			System.out.println("Capital per class: " + capitalPerClass); //TODO
			System.out.println("Differenza " + element.getName() + " " + differencePerClass); //TODO
			if(!toRebalance && differencePerClass.abs().doubleValue() > 2.0) {
				toRebalance = true;
			}
		}
		if(toRebalance) {
			this.rebalance(assetsPerClassMap, financialDataMap, currentPortfolio, capital, differencePerClassMap, capitalPerClassMap);
		}
		return toRebalance;
	}

	private void rebalance(Map<Long, List<AssetEntity>> assetPerClassMap, Map<Long, FinancialDataEntity> financialDataMap,
						  List<PortfolioEntity> currentPortfolio, CapitalEntity capital,
						   Map<Long, BigDecimal> differencePerClassMap, Map<Long, BigDecimal> capitalPerClassMap) {
		Map<Long, PortfolioEntity> portfolioMap = Mapper.getMapPortfolio(currentPortfolio);
		for(Long id : differencePerClassMap.keySet()) {
			BigDecimal capitalPerClass = capitalPerClassMap.get(id);
			BigDecimal capitalDifferencePerClass = this.getCapitalDifferencePerClass(differencePerClassMap.get(id), capital.getAmount());
			System.out.println("Old Capital per: " + id + " è " + capitalPerClass); //TODO
			System.out.println("Capital Difference per: " + id + " è " + capitalDifferencePerClass); //TODO
			for(AssetEntity asset : assetPerClassMap.get(id)) {
				FinancialDataEntity financialData = financialDataMap.get(asset.getId());
				PortfolioEntity portfolio = portfolioMap.get(asset.getId());
				BigDecimal currentUnits = portfolio.getUnits();
				BigDecimal currentValue = currentUnits.multiply(financialData.getValue());
				BigDecimal capitalPerAsset = capitalPerClass.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
				BigDecimal oldCapitalDifferencePerAsset = capitalPerAsset.subtract(currentValue);
				BigDecimal newUnitsOld = oldCapitalDifferencePerAsset.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
				System.out.println("The capital for " + asset.getName() + " is " + capitalPerAsset);
				System.out.println("Differenza " + oldCapitalDifferencePerAsset);
				System.out.println("Nuove unità rispetto al vecchio " + newUnitsOld);
				currentUnits = currentUnits.add(newUnitsOld);


				BigDecimal capitalDifferencePerAsset = capitalDifferencePerClass.divide(new BigDecimal(100.0), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
				System.out.println("Asset difference per " + asset.getName() + " è " + capitalDifferencePerAsset); //TODO
				System.out.println("Avevo: " + currentUnits); //TODO
				System.out.println("Valore: " + currentUnits.multiply(financialData.getValue())); //TODO
				BigDecimal newUnits = capitalDifferencePerAsset.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
				BigDecimal units = currentUnits.subtract(newUnits);
				BigDecimal value = units.multiply(financialData.getValue());
				System.out.println("Ho: " + units); //TODO
				System.out.println("Valore: " + value); //TODO
				portfolio.setUnits(units);
				portfolio.setValue(value);
				System.out.println("--------"); //TODO
			}
			System.out.println("--------\n");  //TODO
		}
		this.portfolioRep.save(currentPortfolio);

	}

	private BigDecimal getCapitalDifferencePerClass(BigDecimal difference, BigDecimal totalCapital) {
		BigDecimal result = totalCapital.divide(new BigDecimal(100.0), 8, RoundingMode.HALF_UP).multiply(difference);
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
