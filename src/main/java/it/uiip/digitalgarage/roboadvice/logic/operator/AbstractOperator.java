package it.uiip.digitalgarage.roboadvice.logic.operator;


import it.uiip.digitalgarage.roboadvice.logic.converter.*;
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
	
	protected UserRepository userRep;
	
	protected DefaultStrategyRepository defaultStrategyRep;

	protected CustomStrategyRepository customStrategyRep;

	protected PortfolioRepository portfolioRep;

	protected GenericConverter<UserEntity, UserDTO> userConv = new UserConverter();
		
	protected GenericConverter<AssetClassEntity, AssetClassDTO> assetClassConv = new AssetClassConverter();
	
	protected GenericConverter<AssetEntity, AssetDTO> assetConv = new AssetConverter();
	
	protected GenericConverter<FinancialDataEntity, FinancialDataDTO> financialDataConv = new FinancialDataConverter();

	protected GenericConverter<PortfolioEntity, PortfolioDTO> portfolioConv = new PortfolioConverter();
	
	protected CustomStrategyConverter customStrategyConv = new CustomStrategyConverter();
	
}
