package it.uiip.digitalgarage.roboadvice.service.controller;

import org.springframework.beans.factory.annotation.Autowired;

import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

public abstract class GenericController {

	@Autowired
	protected AssetRepository daoAsset;
	
	@Autowired
	protected AssetClassRepository daoAssetClass;
	
	@Autowired
	protected FinancialDataRepository daoFinancialData;
	
	@Autowired
	protected UserRepository daoUser;
	
}
