package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.service.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PortfolioOperator extends AbstractOperator {

    public PortfolioDTO getCurrentPortfolio(Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		return this.getCurrentPortfolio(user);
	}

	public PortfolioDTO getCurrentPortfolio(UserEntity user) {
		List<PortfolioEntity> entityList = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
		if(entityList.isEmpty()) {
			return null;
		}
		PortfolioDTO response = this.portfolioWrap.wrapToDTO(entityList);
		return response;
	}

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
		Map<String, List<PortfolioEntity>> map = new HashMap<>();
		for (PortfolioEntity entity : entityList) {
			if(map.get(entity.getDate().toString()) == null) {
				map.put(entity.getDate().toString(), new ArrayList<>());
			}
			map.get(entity.getDate().toString()).add(entity);
		}
		List<PortfolioDTO> list = new ArrayList<>();
		for (String date : map.keySet()) {
			PortfolioDTO dto = (PortfolioDTO) this.portfolioWrap.wrapToDTO(map.get(date));
			list.add(dto);
		}
		Collections.sort(list);
        return list;
    }

    public boolean createUserPortfolio(UserEntity user) {
    	CapitalEntity capitalEntity = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
    	if(capitalEntity == null) {
    		return false;
    	}
    	BigDecimal amount = capitalEntity.getAmount();
    	List<CustomStrategyEntity> strategyEntity = this.customStrategyRep.findByUserAndActive(user, true);
    	if(strategyEntity.isEmpty()) {
    		return false;
    	}
    	for (CustomStrategyEntity strategy : strategyEntity) {
			BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			AssetClassEntity assetClass = strategy.getAssetClass();
			this.savePortfolioForAssetClass(assetClass, user, amountPerClass);
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
    
    public BigDecimal evaluatePortfolio(UserEntity user) {
    	List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
    	if(currentPortfolio.isEmpty()) {
    		return null;
    	}
    	BigDecimal amount = new BigDecimal(0);
    	for (PortfolioEntity element : currentPortfolio) {
			FinancialDataEntity data = this.financialDataRep.findByAssetAndDate(element.getAsset(), element.getAsset().getLastUpdate());
			BigDecimal amountPerAsset = element.getUnits().multiply(data.getValue());
			amount = amount.add(amountPerAsset);
		}
    	return amount;
    }

    public boolean computeUserPortfolio(UserEntity user) {
    	List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
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
    
    public void savePortfolio(List<PortfolioEntity> entities) {
    	for (PortfolioEntity entity : entities) {
    		PortfolioEntity savedEntity = this.portfolioRep.findByUserAndAssetAndDate(entity.getUser(), entity.getAsset(), LocalDate.now());
    		if(savedEntity != null) {
	    		this.portfolioRep.delete(savedEntity);
	    	}
    		this.portfolioRep.save(entity);
		}
    }
   
    //TODO At this moment this method is unuseful
//  public PortfolioDTO getUserPortfolioDate(PortfolioRequestForDateDTO request) {
//		LocalDate date = LocalDate.parse(request.getDate());
//		List<PortfolioEntity> entityList = this.portfolioRep.findByUserIdAndDate(request.getId(), date);
//		if(entityList.isEmpty()) {
//			return null;
//		}
//		PortfolioDTO response = this.portfolioWrap.wrapToDTO(entityList);
//  	return response;
//	}
    
}
