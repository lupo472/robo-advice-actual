package it.uiip.digitalgarage.roboadvice.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

@CrossOrigin("*")
@RestController
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
