package it.uiip.digitalgarage.roboadvice.service.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssetClassControllerTest.class, AssetControllerTest.class, CapitalControllerTest.class,
		CustomStrategyControllerTest.class, DefaultStrategyControllerTest.class, FinancialDataTest.class,
		PortofolioControllerTest.class, UserControllerTest.class })
public class AllTests {

}
