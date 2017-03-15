package it.uiip.digitalgarage.roboadvice.logic.operator;

import org.springframework.beans.factory.annotation.Autowired;

import it.uiip.digitalgarage.roboadvice.logic.converter.*;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.CustomStrategyWrapper;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.PortfolioWrapper;
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
	
	protected AssetClassConverter assetClassConv = new AssetClassConverter();
	
	protected UserConverter userConv = new UserConverter();
	
	protected CapitalConverter capitalConv = new CapitalConverter();	
	
	protected PortfolioWrapper portfolioWrap = new PortfolioWrapper();

	protected CustomStrategyWrapper customStrategyWrap = new CustomStrategyWrapper();
	
}
