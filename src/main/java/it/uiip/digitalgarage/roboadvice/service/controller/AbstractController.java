package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;


import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

public abstract class AbstractController {

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

	
	protected UserOperator userOp;
	
	protected QuandlOperator quandlOp;
	
	protected AssetClassOperator assetClassOp;
	
	protected AssetOperator assetOp;
	
	protected DefaultStrategyOperator defaultStrategyOp;

	protected CustomStrategyOperator customStrategyOp;
	
}
