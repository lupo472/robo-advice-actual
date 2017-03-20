package it.uiip.digitalgarage.roboadvice.test;

import it.uiip.digitalgarage.roboadvice.test.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssetClassOperatorTest.class, CapitalOperatorTest.class,
		CustomStrategyOperatorTest.class, DefaultStrategyOperatorTest.class, FinancialDataTest.class,
		PortfolioOperatorTest.class, UserOperatorTest.class})
public class TestSuite {

}
