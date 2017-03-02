package it.uiip.digitalgarage.roboadvice.logic.operator;


import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import it.uiip.digitalgarage.roboadvice.logic.converter.AssetClassConverter;
import it.uiip.digitalgarage.roboadvice.logic.converter.AssetConverter;
import it.uiip.digitalgarage.roboadvice.logic.converter.GenericConverter;
import it.uiip.digitalgarage.roboadvice.logic.converter.UserConverter;
import it.uiip.digitalgarage.roboadvice.logic.converter.UserLoggedConverter;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

public abstract class AbstractOperator {
	
	protected AssetRepository assetRep;
	
	protected AssetClassRepository assetClassRep;
	
	protected FinancialDataRepository financialDataRep;
	
	protected UserRepository userRep;
	
	protected DefaultStrategyRepository defaultStrategyRep;

	protected CustomStrategyRepository customStrategyRep;

	protected GenericConverter<UserEntity, UserDTO> userConverter = new UserConverter();
	
	protected GenericConverter<UserEntity, UserLoggedDTO> userLoggedConv = new UserLoggedConverter();
	
	protected GenericConverter<AssetClassEntity, AssetClassDTO> assetClassConv = new AssetClassConverter();
	
	protected GenericConverter<AssetEntity, AssetDTO> assetConv = new AssetConverter();
	
}
