package it.uiip.digitalgarage.roboadvice.test;

import it.uiip.digitalgarage.roboadvice.test.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssetClassControllerTest.class, AssetControllerTest.class, /*CapitalControllerTest.class,
		CustomStrategyControllerTest.class,*/ DefaultStrategyControllerTest.class, FinancialDataTest.class,
		PortofolioControllerTest.class, /*UserControllerTest.class, AuthControllerTest.class*/})
public class TestSuite {

}
