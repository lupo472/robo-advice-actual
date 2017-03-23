package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.service.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PortfolioOperator extends AbstractOperator {

	@Cacheable("currentPortfolio")
    public PortfolioDTO getCurrentPortfolio(Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		return this.getCurrentPortfolio(user);
	}

	@Cacheable("currentPortfolio")
	public PortfolioDTO getCurrentPortfolio(UserEntity user) {
		List<PortfolioEntity> entityList = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
		if(entityList.isEmpty()) {
			return null;
		}
		Map<Long, BigDecimal> assetClassMap = new HashMap<>();
		for(PortfolioEntity entity : entityList) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), this.portfolioRep.sumValuesForAssetClass(entity.getAssetClass(), user, LocalDate.now()).getValue());
			}
		}
		BigDecimal totalValue = this.portfolioRep.sumValues(user, LocalDate.now()).getValue();
		return this.portfolioWrap.wrapToDTO(user, entityList, totalValue, assetClassMap);
	}

	@Cacheable("portfolioHistory")
    public List<PortfolioDTO> getPortfolioForPeriod(PeriodRequestDTO request, Authentication auth){
		UserEntity user = this.userRep.findByEmail(auth.getName());
		List<PortfolioEntity> entityList = null;
		if(request.getPeriod() == 0){
			entityList = this.portfolioRep.findByUser(user);
		} else {
			LocalDate initialDate = LocalDate.now();
			LocalDate finalDate = initialDate.minus(Period.ofDays(request.getPeriod() - 1));
			entityList = this.portfolioRep.findByUserAndDateBetween(user, finalDate, initialDate);
		}
		if (entityList.isEmpty()) {
			return null;
		}
		Map<LocalDate, BigDecimal> totalMap = Mapper.getMapValues(this.portfolioRep.sumValues(user));
		Map<Long, Map<LocalDate, BigDecimal>> assetClassMap = new HashMap<>();
		Map<String, Set<PortfolioEntity>> map = this.createMap(user, entityList, assetClassMap);
		List<PortfolioDTO> result = this.portfolioWrap.wrapToDTOList(user, entityList, assetClassMap, map, totalMap);
		Collections.sort(result);
        return result;
    }

	private Map<String, Set<PortfolioEntity>> createMap(UserEntity user, List<PortfolioEntity> entityList, Map<Long, Map<LocalDate, BigDecimal>> assetClassMap) {
		Map<String, Set<PortfolioEntity>> map = new HashMap<>();
		for (PortfolioEntity entity : entityList) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), Mapper.getMapValues(this.portfolioRep.sumValuesForAssetClass(entity.getAssetClass(), user)));
			}
			if(map.get(entity.getDate().toString()) == null) {
				map.put(entity.getDate().toString(), new HashSet<>());
			}
			map.get(entity.getDate().toString()).add(entity);
		}
		return map;
	}

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
    public boolean createUserPortfolio(UserEntity user, List<CustomStrategyEntity> strategyEntity, CapitalEntity capital,
									   Map<Long, List<AssetEntity>> mapAssets, Map<Long, FinancialDataEntity> mapFD) {
		if(strategyEntity.isEmpty()) {
			return false;
		}
    	if(capital == null) {
    		return false;
    	}
    	BigDecimal amount = capital.getAmount();
    	for (CustomStrategyEntity strategy : strategyEntity) {
			BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			AssetClassEntity assetClass = strategy.getAssetClass();
			this.savePortfolioForAssetClass(assetClass, user, amountPerClass, mapAssets, mapFD);
		}
    	return true;
    }

    private void savePortfolioForAssetClass(AssetClassEntity assetClass, UserEntity user, BigDecimal amount,
											Map<Long, List<AssetEntity>> mapAssets, Map<Long, FinancialDataEntity> mapFD) {
    	List<AssetEntity> assets = mapAssets.get(assetClass.getId());
    	List<PortfolioEntity> portfolioList = this.portfolioRep.findByUserAndDate(user, LocalDate.now());
		Map<Long, PortfolioEntity> portfolioMap = Mapper.getMapPortfolio(portfolioList);
    	for (AssetEntity asset : assets) {
			PortfolioEntity savedEntity = portfolioMap.get(asset.getId());
			PortfolioEntity entity = new PortfolioEntity();
			if(savedEntity != null) {
				entity = savedEntity;
			}
    		BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
    		entity.setAsset(asset);
    		entity.setAssetClass(assetClass);
    		entity.setUser(user);
    		entity.setValue(amountPerAsset);
    		entity.setUnits(this.getUnitsForAsset(asset, amountPerAsset, mapFD));
    		entity.setDate(LocalDate.now());
			this.portfolioRep.save(entity);
    	}
    }

    private BigDecimal getUnitsForAsset(AssetEntity asset, BigDecimal amount, Map<Long, FinancialDataEntity> map) {
    	FinancialDataEntity financialData = map.get(asset.getId());
		BigDecimal units = amount.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
		return units;
    }

    public BigDecimal evaluatePortfolio(UserEntity user, Map<Long, FinancialDataEntity> map, List<PortfolioEntity> currentPortfolio) {
		if(currentPortfolio.isEmpty()) {
    		return null;
    	}
    	BigDecimal amount = new BigDecimal(0);
    	for (PortfolioEntity element : currentPortfolio) {
			FinancialDataEntity data = map.get(element.getAsset().getId());
			BigDecimal amountPerAsset = element.getUnits().multiply(data.getValue());
			amount = amount.add(amountPerAsset);
		}
    	return amount;
    }

    @CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
    public boolean computeUserPortfolio(UserEntity user, List<PortfolioEntity> currentPortfolio, Map<Long, FinancialDataEntity> map) {
    	List<PortfolioEntity> newPortfolioList = new ArrayList<>();
    	for (PortfolioEntity element : currentPortfolio) {
    		BigDecimal units = element.getUnits();
    		BigDecimal newValue = this.getValueForAsset(units, element.getAsset(), map);
    		if(newValue == null) {
    			return false;
    		}
    		PortfolioEntity newElement = new PortfolioEntity();
    		newElement.setAsset(element.getAsset());
    		newElement.setAssetClass(element.getAssetClass());
    		newElement.setUser(user);
    		newElement.setUnits(units);
    		newElement.setValue(newValue);
    		newElement.setDate(LocalDate.now());
    		newPortfolioList.add(newElement);
    	}
    	this.savePortfolio(newPortfolioList);
    	return true;
    }

     private BigDecimal getValueForAsset(BigDecimal units, AssetEntity asset, Map<Long, FinancialDataEntity> map) {
    	FinancialDataEntity financialData = map.get(asset.getId());
    	if(financialData == null) {
    		return null;
    	}
    	BigDecimal result = units.multiply(financialData.getValue());
    	return result;
    }

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
    public void savePortfolio(List<PortfolioEntity> entities) {
    	for (PortfolioEntity entity : entities) {
    		PortfolioEntity savedEntity = this.portfolioRep.findByUserAndAssetAndDate(entity.getUser(), entity.getAsset(), LocalDate.now());
    		if(savedEntity != null) {
	    		this.portfolioRep.delete(savedEntity);
	    	}
    		this.portfolioRep.save(entity);
		}
    }
    
}
