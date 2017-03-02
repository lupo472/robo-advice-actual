package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericController {

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

	
}
