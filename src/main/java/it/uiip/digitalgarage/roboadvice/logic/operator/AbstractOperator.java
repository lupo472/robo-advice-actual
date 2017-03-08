package it.uiip.digitalgarage.roboadvice.logic.operator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.logic.converter.*;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.CustomStrategyWrapper;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.GenericWrapper;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.PortfolioWrapper;
import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.*;

public abstract class AbstractOperator {
	
	protected AssetRepository assetRep;
	
	protected AssetClassRepository assetClassRep;
	
	protected FinancialDataRepository financialDataRep;
	
	@Autowired
	protected UserRepository userRep;
	
	protected DefaultStrategyRepository defaultStrategyRep;

	protected CustomStrategyRepository customStrategyRep;

	protected PortfolioRepository portfolioRep;
	
	protected CapitalRepository capitalRep;

	protected GenericConverter<UserEntity, UserDTO> userConv = new UserConverter();
		
	protected GenericConverter<AssetClassEntity, AssetClassDTO> assetClassConv = new AssetClassConverter();
	
	protected GenericConverter<AssetEntity, AssetDTO> assetConv = new AssetConverter();
	
	protected GenericConverter<FinancialDataEntity, FinancialDataDTO> financialDataConv = new FinancialDataConverter();

	protected GenericConverter<CapitalEntity, CapitalDTO> capitalConv = new CapitalConverter();
	
	protected PortfolioWrapper portfolioWrap = new PortfolioWrapper();

	protected GenericWrapper<CustomStrategyEntity, CustomStrategyDTO> customStrategyWrap = new CustomStrategyWrapper();
	
}
