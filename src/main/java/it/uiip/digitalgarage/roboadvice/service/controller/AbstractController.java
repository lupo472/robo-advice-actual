package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is just a container of properties.
 *
 * @author Cristian Laurini
 */
public abstract class AbstractController {

	@Autowired
	protected UserOperator userOp;
	
	@Autowired
	protected QuandlOperator quandlOp;
	
	@Autowired
	protected FinancialDataOperator financialDataOp;
	
	@Autowired
	protected AssetClassOperator assetClassOp;
	
	@Autowired
	protected DefaultStrategyOperator defaultStrategyOp;

	@Autowired
	protected CustomStrategyOperator customStrategyOp;

	@Autowired
	protected PortfolioOperator portfolioOp;

	@Autowired
	protected CapitalOperator capitalOp;

	@Autowired
	protected BacktestingOperator backtestingOp;

	@Autowired
	protected ForecastingOperator forecastingOp;

	@Autowired
	protected SchedulingOperator schedulingOp;

	@Autowired
	protected AdviceOperator adviceOp;

}
