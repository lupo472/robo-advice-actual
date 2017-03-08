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

//	@Autowired
//	protected AssetRepository assetRep;
//	
//	@Autowired
//	protected AssetClassRepository assetClassRep;
//	
//	@Autowired
//	protected FinancialDataRepository financialDataRep;
//	
//	//Remove
//	@Autowired
//	protected UserRepository userRep;
//	
////	@Autowired
////	protected DefaultStrategyRepository defaultStrategyRep;
//
//	@Autowired
//	protected CustomStrategyRepository customStrategyRep;
//
//	@Autowired
//	protected PortfolioRepository portfolioRep;
//	
//	@Autowired
//	protected CapitalRepository capitalRep;

	@Autowired
	protected UserOperator userOp;
	
	@Autowired
	protected QuandlOperator quandlOp;
	
	@Autowired
	protected FinancialDataOperator financialDataOp;
	
	@Autowired
	protected AssetClassOperator assetClassOp;
	
	@Autowired
	protected AssetOperator assetOp;
	
	@Autowired
	protected DefaultStrategyOperator defaultStrategyOp;

	@Autowired
	protected CustomStrategyOperator customStrategyOp;

	@Autowired
	protected PortfolioOperator portfolioOp;

	@Autowired
	protected CapitalOperator capitalOp;
	
}
