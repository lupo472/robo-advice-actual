package it.uiip.digitalgarage.roboadvice.test;

import it.uiip.digitalgarage.roboadvice.test.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssetClassOperatorTest.class/*, CapitalOperatorTest.class*/,
		CustomStrategyOperatorTest.class, DefaultStrategyControllerTest.class, FinancialDataTest.class,
		PortfolioOperatorTest.class, /*UserControllerTest.class, AuthControllerTest.class*/})
public class TestSuite {

}
