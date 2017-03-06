package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PortfolioOperator extends AbstractOperator {

    public PortfolioOperator(PortfolioRepository portfolioRep){
        this.portfolioRep = portfolioRep;
    }
    
    public PortfolioOperator(PortfolioRepository portfolioRep, CapitalRepository capitalRep, CustomStrategyRepository customStrategyRep, 
    		AssetRepository assetRep, FinancialDataRepository financialDataRep) {
        this.portfolioRep = portfolioRep;
        this.capitalRep = capitalRep;
        this.customStrategyRep = customStrategyRep;
        this.assetRep = assetRep;
        this.financialDataRep = financialDataRep;
    }

    public PortfolioDTO getUserCurrentPortfolio(UserLoggedDTO dto) {
        LocalDate date = LocalDate.now();
        List<PortfolioEntity> entityList = this.portfolioRep.findByUserIdAndDate(dto.getId(), date);
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
    	BigDecimal amount = this.capitalConv.convertToDTO(capitalEntity).getAmount();
    	List<CustomStrategyEntity> strategyEntity = this.customStrategyRep.findByUserIdAndActive(user.getId(), true);
    	if(strategyEntity.isEmpty()) {
    		return false;
    	}
    	CustomStrategyDTO strategy = this.customStrategyWrap.wrapToDTO(strategyEntity);
    	for (AssetClassStrategyDTO element : strategy.getList()) {
    		BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00)).multiply(element.getPercentage());
    		this.savePortfolioForClass(element.getAssetClass(), amountPerClass, user);
		}
    	return true;
    }
    
    private void savePortfolioForClass(AssetClassDTO assetClass, BigDecimal amount, UserLoggedDTO user) {
    	List<AssetDTO> assets = this.assetConv.convertToDTO(this.assetRep.findByAssetClassId(assetClass.getId()));
    	for (AssetDTO asset : assets) {
			BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00)).multiply(asset.getPercentage());
			this.savePortfolioRow(asset, amountPerAsset, user);
		}
    }
    
    private void savePortfolioRow(AssetDTO asset, BigDecimal amount, UserLoggedDTO user) {
    	PortfolioEntity entity = new PortfolioEntity();
    	entity.setAsset(this.assetConv.convertToEntity(asset));
    	entity.setAssetClass(this.assetClassConv.convertToEntity(asset.getAssetClass()));
    	entity.setUser(this.userConv.convertToEntity(user));
    	entity.setValue(amount);
    	entity.setUnits(this.getUnitsForAsset(asset, amount));
    	entity.setDate(LocalDate.now());
    	this.portfolioRep.save(entity);
    }
    
    private BigDecimal getUnitsForAsset(AssetDTO asset, BigDecimal amount) {
    	FinancialDataDTO financialData = this.financialDataConv.convertToDTO(this.financialDataRep.findLastForAnAsset(asset.getId()));
    	BigDecimal units = financialData.getValue().divide(amount);
    	return units;
    }

}
