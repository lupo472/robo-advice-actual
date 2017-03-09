package it.uiip.digitalgarage.roboadvice.logic.operator;

import org.springframework.beans.factory.annotation.Autowired;

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
	
	@Autowired
	protected AssetRepository assetRep;
	
	@Autowired
	protected AssetClassRepository assetClassRep;
	
	@Autowired
	protected FinancialDataRepository financialDataRep;
	
	@Autowired
	protected UserRepository userRep;
	
	@Autowired
	protected DefaultStrategyRepository defaultStrategyRep;

	@Autowired
	protected CustomStrategyRepository customStrategyRep;

	@Autowired
	protected PortfolioRepository portfolioRep;
	
	@Autowired
	protected CapitalRepository capitalRep;
	
	@Autowired
	protected AuthRepository authRep;

	protected GenericConverter<UserEntity, UserDTO> userConv = new UserConverter();
		
	protected GenericConverter<AssetClassEntity, AssetClassDTO> assetClassConv = new AssetClassConverter();
	
	protected GenericConverter<AssetEntity, AssetDTO> assetConv = new AssetConverter();
	
	protected GenericConverter<FinancialDataEntity, FinancialDataDTO> financialDataConv = new FinancialDataConverter();

	protected GenericConverter<CapitalEntity, CapitalDTO> capitalConv = new CapitalConverter();
	
	protected GenericWrapper<PortfolioEntity, PortfolioDTO> portfolioWrap = new PortfolioWrapper();

	protected GenericWrapper<CustomStrategyEntity, CustomStrategyDTO> customStrategyWrap = new CustomStrategyWrapper();
	
}
