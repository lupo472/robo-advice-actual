package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.ValueMap;
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
		SchedulingOperator.count++; //TODO remove counting
		if(entityList.isEmpty()) {
			return null;
		}
		PortfolioDTO result = new PortfolioDTO();
		LocalDate date = entityList.get(0).getDate();
		result.setDate(date.toString());
		BigDecimal total = this.portfolioRep.sumValues(user, date).getValue();
		SchedulingOperator.count++; //TODO remove counting
		Set<PortfolioElementDTO> set = new HashSet<>();
		for (PortfolioEntity entity : entityList) {
			BigDecimal assetClassValue = this.portfolioRep.sumValuesForAssetClass(entity.getAssetClass(), user, date).getValue();
			SchedulingOperator.count++; //TODO remove counting
			PortfolioElementDTO element = new PortfolioElementDTO();
			element.setId(entity.getAssetClass().getId());
			element.setName(entity.getAssetClass().getName());
			element.setValue(assetClassValue);
			element.setPercentage(assetClassValue.divide(total, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100.00)));
			set.add(element);
		}
		List<PortfolioElementDTO> list = new ArrayList<>(set);
		Collections.sort(list);
		result.setList(list);
		return result;
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
		Map<LocalDate, BigDecimal> totalValueMap = ValueMap.getMap(this.portfolioRep.sumValues(user));
		Map<Long, Map<LocalDate, BigDecimal>> assetClassMap = new HashMap<>();
		Map<String, Set<PortfolioEntity>> map = new HashMap<>();
		for (PortfolioEntity entity : entityList) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), ValueMap.getMap(this.portfolioRep.sumValuesForAssetClass(entity.getAssetClass(), user)));
			}
			if(map.get(entity.getDate().toString()) == null) {
				map.put(entity.getDate().toString(), new HashSet<>());
			}
			map.get(entity.getDate().toString()).add(entity);
		}
		List<PortfolioDTO> result = new ArrayList<>();
		for (String date : map.keySet()) {
			PortfolioDTO dto = new PortfolioDTO();
			Set<PortfolioElementDTO> set = new HashSet<>();
			for (PortfolioEntity entity : map.get(date)) {
				PortfolioElementDTO element = new PortfolioElementDTO();
				element.setId(entity.getAssetClass().getId());
				element.setName(entity.getAssetClass().getName());
				BigDecimal value = assetClassMap.get(entity.getAssetClass().getId()).get(LocalDate.parse(date));
				BigDecimal percentage = value.divide(totalValueMap.get(LocalDate.parse(date)), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100.00));
				element.setValue(value);
				element.setPercentage(percentage);
				set.add(element);
			}
			dto.setDate(date);
			List<PortfolioElementDTO> list = new ArrayList<>(set);
			Collections.sort(list);
			dto.setList(list);
			result.add(dto);
		}
		Collections.sort(result);
        return result;
    }

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory"}, allEntries = true)
	public boolean createUserPortfolio(UserEntity user) {
		CapitalEntity capitalEntity = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
		if(capitalEntity == null) {
			return false;
		}
		BigDecimal amount = capitalEntity.getAmount();
		List<CustomStrategyEntity> strategyEntity = this.customStrategyRep.findByUserAndActive(user, true);
		return this.createUserPortfolio(user, strategyEntity);
	}

    @CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory"}, allEntries = true)
    public boolean createUserPortfolio(UserEntity user, List<CustomStrategyEntity> strategyEntity) {
		if(strategyEntity.isEmpty()) {
			return false;
		}
    	CapitalEntity capitalEntity = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
		SchedulingOperator.count++; //TODO remove counting
    	if(capitalEntity == null) {
    		return false;
    	}
    	BigDecimal amount = capitalEntity.getAmount();
    	for (CustomStrategyEntity strategy : strategyEntity) {
			BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			AssetClassEntity assetClass = strategy.getAssetClass();
			this.savePortfolioForAssetClass(assetClass, user, amountPerClass);
			SchedulingOperator.count++; //TODO remove counting
		}
    	return true;
    }
    
    private void savePortfolioForAssetClass(AssetClassEntity assetClass, UserEntity user, BigDecimal amount) {
    	List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
    	for (AssetEntity asset : assets) {
    		BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
    		PortfolioEntity entity = new PortfolioEntity();
    		entity.setAsset(asset);
    		entity.setAssetClass(assetClass);
    		entity.setUser(user);
    		entity.setValue(amountPerAsset);
    		entity.setUnits(this.getUnitsForAsset(asset, amountPerAsset));
    		entity.setDate(LocalDate.now());
    		PortfolioEntity savedEntity = this.portfolioRep.findByUserAndAssetAndDate(user, asset, LocalDate.now());
	    	if(savedEntity != null) {
	    		this.portfolioRep.delete(savedEntity);
	    	}
	    	this.portfolioRep.save(entity);
    	}
    }
    
    private BigDecimal getUnitsForAsset(AssetEntity asset, BigDecimal amount) {
    	FinancialDataEntity financialData = this.financialDataRep.findByAssetAndDate(asset, asset.getLastUpdate());
		BigDecimal units = amount.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
		return units;
    }

    /* TODO: migliorare prestazioni
     * 		 Ogni volta viene fatta una query per caricare il corretto financialData.
     * 		 Tutto ciò viene ripetuto per ogni utente e per ogni asset nel suo portfolio.
     * 		 E' possibile ottenere una sola volta la lista dei financialDataEntity
     * 		 correnti dal database e salvarli in una mappa da cui prenderli all'occorrenza.
     */

    public BigDecimal evaluatePortfolio(UserEntity user) {
    	List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
		//TODO remove counting
		SchedulingOperator.count++;
    	if(currentPortfolio.isEmpty()) {
    		return null;
    	}
    	BigDecimal amount = new BigDecimal(0);
    	for (PortfolioEntity element : currentPortfolio) {
			FinancialDataEntity data = this.financialDataRep.findByAssetAndDate(element.getAsset(), element.getAsset().getLastUpdate());
			//TODO remove counting
			SchedulingOperator.count++;
			BigDecimal amountPerAsset = element.getUnits().multiply(data.getValue());
			amount = amount.add(amountPerAsset);
		}
    	return amount;
    }

    /*TODO: migliorare prestazioni
	 *    	Il currentPortfolio viene già computato dallo scheduler e può dunque essere
	 *    	ottenuto come parametro o cachato, evitando una query.
     */
	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory"}, allEntries = true)
    public boolean computeUserPortfolio(UserEntity user) {
    	List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
		//TODO remove counting
		SchedulingOperator.count++;
    	List<PortfolioEntity> newPortfolioList = new ArrayList<>();
    	for (PortfolioEntity element : currentPortfolio) {
    		BigDecimal units = element.getUnits();
    		BigDecimal newValue = this.getValueForAsset(units, element.getAsset());
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
		//TODO remove counting
		SchedulingOperator.count++;
    	return true;
    }
  
    private BigDecimal getValueForAsset(BigDecimal units, AssetEntity asset) {
    	FinancialDataEntity financialData = this.financialDataRep.findByAssetAndDate(asset, asset.getLastUpdate());
    	if(financialData == null) {
    		return null;
    	}
    	BigDecimal result = units.multiply(financialData.getValue());
    	return result;
    }

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory"}, allEntries = true)
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
