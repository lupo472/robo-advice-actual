package it.uiip.digitalgarage.roboadvice.test;

import it.uiip.digitalgarage.roboadvice.test.controller.*;
import it.uiip.digitalgarage.roboadvice.test.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssetClassOperatorTest.class, CapitalOperatorTest.class,
		CustomStrategyOperatorTest.class, DefaultStrategyOperatorTest.class, FinancialDataOperatorTest.class,
		PortfolioOperatorTest.class, UserOperatorTest.class, CustomStrategyControllerTest.class, CapitalControllerTest.class,
		DefaultStrategyControllerTest.class, AssetClassControllerTest.class, UserControllerTest.class})
public class TestSuite {

}
