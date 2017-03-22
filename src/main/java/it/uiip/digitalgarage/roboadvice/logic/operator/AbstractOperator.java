package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.logic.wrapper.PortfolioWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import it.uiip.digitalgarage.roboadvice.logic.converter.*;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.CustomStrategyWrapper;
import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

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
	protected AssetClassConverter assetClassConv;
	
	@Autowired
	protected UserConverter userConv;
	
	@Autowired
	protected CapitalConverter capitalConv;

	@Autowired
	protected CustomStrategyWrapper customStrategyWrap;

	@Autowired
	protected PortfolioWrapper portfolioWrap;

}
