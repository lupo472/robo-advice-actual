package it.uiip.digitalgarage.roboadvice.test.service;

import static org.junit.Assert.*;

import it.uiip.digitalgarage.roboadvice.logic.operator.CustomStrategyOperator;
import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.PortfolioController;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class PortfolioOperatorTest {

	/* TODO
	 * Test: createUserPortfolio
	 * Test: computeUserPortfolio
	 * Test: getUserPortfolioPeriod
	*/

	@InjectMocks
	@Autowired
	private PortfolioOperator portfolioOp;

	@Mock
	private CapitalRepository capitalRep;

	@Mock
	private CustomStrategyRepository customStrategyRep;

	@Mock
	private PortfolioRepository portfolioRep;

	@Mock
	private AssetRepository assetRep;

	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserPortfolioTest() {
		UserEntity user = new UserEntity();
		user.setId(new Long(2));
		user.setEmail("luca@antilici.it");
		user.setPassword("pippo123");
		user.setDate(LocalDate.now());
		user.setLastUpdate(LocalDate.now());

		CapitalEntity capitalEntity = new CapitalEntity();
		capitalEntity.setId(new Long(12));
		capitalEntity.setUser(user);
		capitalEntity.setAmount(new BigDecimal(1236.34));
		capitalEntity.setDate(LocalDate.now());
	}
	
	@Test
	public void getUserCurrentPortfolioOK() {
//		UserRegisteredDTO user = new UserRegisteredDTO();
//		user.setId(new Long(35));
//		user.setEmail("cristian.laurini@gmail.com");
//		user.setPassword("cristianlaurini");
//		GenericResponse<?> response = this.portfolioCtrl.getUserCurrentPortfolio(user);
//		PortfolioDTO portfolio = (PortfolioDTO) response.getData();
//		assertEquals(1, response.getResponse());
//		assertEquals(new Long(35), portfolio.getIdUser());
//		assertEquals(true, portfolio.getList().size() > 0);
	}
	
	@Test
	public void getUserCurrentPortfolioProblem() {
//		UserRegisteredDTO user = new UserRegisteredDTO();
//		user.setId(new Long(15));
//		user.setEmail("criidd@a");
//		user.setPassword("ceaaaaaa");
//		GenericResponse<?> response = this.portfolioCtrl.getUserCurrentPortfolio(user);
//		assertEquals(0, response.getResponse());
//		assertEquals(ControllerConstants.EMPTY_PORTFOLIO, response.getData());
	}
	
	public void getUserPortfolioDateOK() {
//		PortfolioRequestForDateDTO request = new PortfolioRequestForDateDTO();
//		request.setId(new Long(35));
//		request.setDate("2017-03-07");
//		GenericResponse<?> response = this.portfolioCtrl.getUserPortfolioDate(request);
//		PortfolioDTO portfolio = (PortfolioDTO) response.getData();
//		assertEquals(1, response.getResponse());
//		assertEquals(new Long(35), portfolio.getIdUser());
//		assertEquals("2017-03-07", portfolio.getDate());
//		assertEquals(13, portfolio.getList().size());
	}
	
	@Test
	public void getUserPortfolioDateProblem() {
//		PortfolioRequestForDateDTO request = new PortfolioRequestForDateDTO();
//		request.setId(new Long(15));
//		request.setDate("2017-03-07");
//		GenericResponse<?> response = this.portfolioCtrl.getUserPortfolioDate(request);
//		assertEquals(0, response.getResponse());
//		assertEquals(ControllerConstants.EMPTY_PORTFOLIO, response.getData());
	}
	
}
