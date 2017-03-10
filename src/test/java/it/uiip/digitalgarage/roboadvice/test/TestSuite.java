package it.uiip.digitalgarage.roboadvice.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import it.uiip.digitalgarage.roboadvice.test.service.AssetClassControllerTest;
import it.uiip.digitalgarage.roboadvice.test.service.AssetControllerTest;
import it.uiip.digitalgarage.roboadvice.test.service.CapitalTest;
import it.uiip.digitalgarage.roboadvice.test.service.CustomStrategyControllerTest;
import it.uiip.digitalgarage.roboadvice.test.service.DefaultStrategyControllerTest;
import it.uiip.digitalgarage.roboadvice.test.service.FinancialDataTest;
import it.uiip.digitalgarage.roboadvice.test.service.PortofolioControllerTest;
import it.uiip.digitalgarage.roboadvice.test.service.UserControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ AssetClassControllerTest.class, AssetControllerTest.class, CapitalTest.class,
		CustomStrategyControllerTest.class, DefaultStrategyControllerTest.class, FinancialDataTest.class,
		PortofolioControllerTest.class, UserControllerTest.class })
public class TestSuite {

}
