package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioOperator extends AbstractOperator {

    public PortfolioOperator(PortfolioRepository portfolioRep){
        this.portfolioRep = portfolioRep;
    }
    
    public PortfolioOperator(PortfolioRepository portfolioRep, FinancialDataRepository financialDataRep) {
    	this.portfolioRep = portfolioRep;
    	this.financialDataRep = financialDataRep;
    }
    
    public PortfolioOperator(PortfolioRepository portfolioRep, CapitalRepository capitalRep, CustomStrategyRepository customStrategyRep, 
    		AssetRepository assetRep, FinancialDataRepository financialDataRep, UserRepository userRep) {
        this.portfolioRep = portfolioRep;
        this.capitalRep = capitalRep;
        this.customStrategyRep = customStrategyRep;
        this.assetRep = assetRep;
        this.financialDataRep = financialDataRep;
        this.userRep = userRep;
    }

    public PortfolioDTO getUserCurrentPortfolio(UserLoggedDTO dto) {
        List<PortfolioEntity> entityList = this.portfolioRep.findLastPortfolioForUser(dto.getId());
        if(entityList.isEmpty()) {
        	return null;
        }
        PortfolioDTO response = this.portfolioWrap.wrapToDTO(entityList);
        return response;
    }

    public List<PortfolioDTO> getUserPortfolioPeriod(DataRequestDTO request){
		List<PortfolioDTO> response = new ArrayList<PortfolioDTO>();
		List<PortfolioEntity> entityList;
		if(request.getPeriod() == 0){
			entityList = this.portfolioRep.findByUserId(request.getId());
		}
		else {
			LocalDate initialDate = LocalDate.now();
			LocalDate finalDate = initialDate.minus(Period.ofDays(request.getPeriod() - 1));
			entityList = this.portfolioRep.findByUserIdAndDateBetween(request.getId(), finalDate, initialDate);
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
        return list;
    }

    public PortfolioDTO getUserPortfolioDate(PortfolioRequestDTO request) {
		LocalDate date = LocalDate.parse(request.getDate());
		List<PortfolioEntity> entityList = this.portfolioRep.findByUserIdAndDate(request.getId(), date);
		if(entityList.isEmpty()) {
			return null;
		}
		PortfolioDTO response = this.portfolioWrap.wrapToDTO(entityList);
    	return response;
	}

    public boolean createUserPortfolio(UserLoggedDTO user) {
    	CapitalEntity capitalEntity = this.capitalRep.findLast(user.getId());
    	if(capitalEntity == null) {
    		return false;
    	}
    	BigDecimal amount = capitalEntity.getAmount();
    	List<CustomStrategyEntity> strategyEntity = this.customStrategyRep.findByUserIdAndActive(user.getId(), true);
    	if(strategyEntity.isEmpty()) {
    		return false;
    	}
    	CustomStrategyDTO strategy = this.customStrategyWrap.wrapToDTO(strategyEntity);
    	for (AssetClassStrategyDTO element : strategy.getList()) {
    		BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(element.getPercentage());
    		this.savePortfolioForAssetClass(element.getAssetClass(), amountPerClass, user);
		}
    	return true;
    }
    
    private void savePortfolioForAssetClass(AssetClassDTO assetClass, BigDecimal amount, UserLoggedDTO user) {
    	List<AssetDTO> assets = this.assetConv.convertToDTO(this.assetRep.findByAssetClassId(assetClass.getId()));
    	for (AssetDTO asset : assets) {
			BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
			PortfolioEntity entity = new PortfolioEntity();
	    	entity.setAsset(this.assetConv.convertToEntity(asset));
	    	entity.setAssetClass(this.assetClassConv.convertToEntity(asset.getAssetClass()));
	    	entity.setUser(this.userRep.findById(user.getId()));
	    	entity.setValue(amountPerAsset);
	    	entity.setUnits(this.getUnitsForAsset(asset, amountPerAsset));
	    	entity.setDate(LocalDate.now());
	    	PortfolioEntity savedEntity = this.portfolioRep.findByUserIdAndAssetIdAndDate(user.getId(), asset.getId(), LocalDate.now());
	    	if(savedEntity != null) {
	    		this.portfolioRep.delete(savedEntity);
	    	} 
	    	this.portfolioRep.save(entity);
		}
    }
    
    private BigDecimal getUnitsForAsset(AssetDTO asset, BigDecimal amount) {
    	FinancialDataDTO financialData = this.financialDataConv.convertToDTO(this.financialDataRep.findLastForAnAsset(asset.getId()));
    	BigDecimal units = amount.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
    	return units;
    }

    public boolean computeUserPortfolio(UserLoggedDTO user) {
    	List<PortfolioEntity> currentPortfolioList = this.portfolioRep.findLastPortfolioForUser(user.getId());
    	for (PortfolioEntity element : currentPortfolioList) {
    		BigDecimal units = element.getUnits();
    		BigDecimal newValue = this.getValueForAsset(units, element.getAsset());
    		if(newValue == null) {
    			return false;
    		}
    		element.setValue(newValue);
    		element.setDate(LocalDate.now());
		}
    	this.savePortfolio(currentPortfolioList);
    	return true;
    }
    
    public BigDecimal evaluatePortfolio(UserLoggedDTO user) {
    	List<PortfolioEntity> currentPortfolioList = this.portfolioRep.findLastPortfolioForUser(user.getId());
    	BigDecimal amount = new BigDecimal(0);
    	for (PortfolioEntity element : currentPortfolioList) {
    		FinancialDataEntity data = this.financialDataRep.findLastForAnAsset(element.getAsset().getId());
    		BigDecimal amountPerAsset = element.getUnits().multiply(data.getValue());
    		amount = amount.add(amountPerAsset);
		}
    	return amount;
    }
    
    public void savePortfolio(List<PortfolioEntity> entities) {
    	for (PortfolioEntity entity : entities) {
    		PortfolioEntity savedEntity = this.portfolioRep.findByUserIdAndAssetIdAndDate(entity.getUser().getId(), entity.getAsset().getId(), LocalDate.now());
	    	if(savedEntity != null) {
	    		this.portfolioRep.delete(savedEntity);
	    	}
    		this.portfolioRep.save(entity);
		}
    }
    
    private BigDecimal getValueForAsset(BigDecimal units, AssetEntity asset) {
    	FinancialDataEntity financialDataEntity = this.financialDataRep.findLastForAnAsset(asset.getId());
    	if(financialDataEntity == null) {
    		return null;
    	}
    	FinancialDataDTO financialData = this.financialDataConv.convertToDTO(financialDataEntity);
    	BigDecimal result = units.multiply(financialData.getValue());
    	return result;
    }
    
}
