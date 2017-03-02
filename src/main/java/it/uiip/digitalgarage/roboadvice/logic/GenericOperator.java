package it.uiip.digitalgarage.roboadvice.logic;

import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

public abstract class GenericOperator {
	
	protected AssetRepository assetRep;
	
	protected AssetClassRepository assetClassRep;
	
	protected FinancialDataRepository financialDataRep;
	
	protected UserRepository userRep;
	
	protected DefaultStrategyRepository defaultStrategyRep;

}
