package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.repository.*;

public abstract class GenericOperator {
	
	protected AssetRepository assetRep;
	
	protected AssetClassRepository assetClassRep;
	
	protected FinancialDataRepository financialDataRep;
	
	protected UserRepository userRep;
	
	protected DefaultStrategyRepository defaultStrategyRep;

	protected CustomStrategyRepository customStrategyRep;

}
