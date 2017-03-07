package it.uiip.digitalgarage.roboadvice.logic.operator;

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
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;

import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class PortfolioOperator extends AbstractOperator {

    public PortfolioOperator(PortfolioRepository portfolioRep){
        this.portfolioRep = portfolioRep;
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
        LocalDate date = LocalDate.now();
        List<PortfolioEntity> entityList = this.portfolioRep.findLastPortfolioForUser(dto.getId());
        if(entityList.isEmpty()) {
        	return null;
        }
        PortfolioDTO response = this.portfolioWrap.wrapToDTO(entityList);
        return response;
    }

    public List<PortfolioDTO> getUserPortfolioPeriod(PortfolioRequestDTO dto){

        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = initialDate.minus(Period.ofDays(dto.getPeriod()));
        List<PortfolioEntity> entityList = this.portfolioRep.findByUserIdAndDateBetween(dto.getIdUser(), finalDate, initialDate);

        if(entityList.isEmpty()){
            return null;
        }

        List<PortfolioDTO> portfolioDTOList = new ArrayList<PortfolioDTO>();

        return null;
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
    		BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 4).multiply(element.getPercentage());
    		this.savePortfolioForClass(element.getAssetClass(), amountPerClass, user);
		}
    	return true;
    }
    
    private void savePortfolioForClass(AssetClassDTO assetClass, BigDecimal amount, UserLoggedDTO user) {
    	List<AssetDTO> assets = this.assetConv.convertToDTO(this.assetRep.findByAssetClassId(assetClass.getId()));
    	for (AssetDTO asset : assets) {
			BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 4).multiply(asset.getPercentage());
			this.savePortfolioRow(asset, amountPerAsset, user);
		}
    }
    
    private void savePortfolioRow(AssetDTO asset, BigDecimal amount, UserLoggedDTO user) {
    	PortfolioEntity entity = new PortfolioEntity();
    	entity.setAsset(this.assetConv.convertToEntity(asset));
    	entity.setAssetClass(this.assetClassConv.convertToEntity(asset.getAssetClass()));
    	entity.setUser(this.userRep.findById(user.getId()));
    	entity.setValue(amount);
    	entity.setUnits(this.getUnitsForAsset(asset, amount));
    	entity.setDate(LocalDate.now());
    	this.portfolioRep.save(entity);
    }
    
    private BigDecimal getUnitsForAsset(AssetDTO asset, BigDecimal amount) {
    	FinancialDataDTO financialData = this.financialDataConv.convertToDTO(this.financialDataRep.findLastForAnAsset(asset.getId()));
    	BigDecimal units = amount.divide(financialData.getValue(), 4);//financialData.getValue().divide(amount, 4);
    	return units;
    }

    public boolean computeUserPortfolio(UserLoggedDTO user) {
    	PortfolioDTO currentPorfolio = this.getUserCurrentPortfolio(user);
    	if(currentPorfolio == null) {
    		System.out.println("Current portfolio == null");
    		return false;
    	}
    	List<PortfolioElementDTO> elements = currentPorfolio.getList();
    	List<PortfolioElementDTO> newPortofolioElements = new ArrayList<>();
    	for (PortfolioElementDTO element : elements) {
    		BigDecimal units = element.getUnits();
    		BigDecimal newValue = this.computeValue(units, element.getAsset());
    		if(newValue == null) {
    			System.out.println("newValue == null");
    			return false;
    		}
    		element.setValue(newValue);
    		newPortofolioElements.add(element);
		}
    	PortfolioDTO newPortfolio = new PortfolioDTO();
    	newPortfolio.setIdUser(user.getId());
    	newPortfolio.setDate(LocalDate.now().toString());
    	newPortfolio.setList(newPortofolioElements);
    	List<PortfolioEntity> entities = this.portfolioWrap.unwrapToEntity(newPortfolio);
    	this.savePortfolio(entities);
    	return true;
    }
    
    public void savePortfolio(List<PortfolioEntity> entities) {
    	for (PortfolioEntity entity : entities) {
			this.savePortfolioRow(this.assetConv.convertToDTO(entity.getAsset()), entity.getValue(), (UserLoggedDTO) this.userConv.convertToDTO(entity.getUser()));
		}
    }
    
    private BigDecimal computeValue(BigDecimal units, AssetDTO asset) {
    	FinancialDataEntity financialDataEntity = this.financialDataRep.findLastForAnAsset(asset.getId());
    	if(financialDataEntity == null) {
    		return null;
    	}
    	FinancialDataDTO financialData = this.financialDataConv.convertToDTO(financialDataEntity);
    	BigDecimal result = units.multiply(financialData.getValue());
    	return result;
    }
    
}
