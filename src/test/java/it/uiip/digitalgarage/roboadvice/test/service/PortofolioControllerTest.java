package it.uiip.digitalgarage.roboadvice.test.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.PortfolioController;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioRequestForDateDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class PortofolioControllerTest {

	/* TODO
	 * Test: createUserPortfolio
	 * Test: computeUserPortfolio
	 * Test: getUserPortfolioPeriod
	*/
	
	@Autowired
	private PortfolioController portfolioCtrl;
	
	@Test
	public void getUserCurrentPortfolioOK() {
		UserRegisteredDTO user = new UserRegisteredDTO();
		user.setId(new Long(35));
		user.setEmail("cristian.laurini@gmail.com");
		user.setPassword("cristianlaurini");
		GenericResponse<?> response = this.portfolioCtrl.getUserCurrentPortfolio(user);
		PortfolioDTO portfolio = (PortfolioDTO) response.getData();
		assertEquals(1, response.getResponse());
		assertEquals(new Long(35), portfolio.getIdUser());
		assertEquals(true, portfolio.getList().size() > 0);
	}
	
	@Test
	public void getUserCurrentPortfolioProblem() {
		UserRegisteredDTO user = new UserRegisteredDTO();
		user.setId(new Long(15));
		user.setEmail("criidd@a");
		user.setPassword("ceaaaaaa");
		GenericResponse<?> response = this.portfolioCtrl.getUserCurrentPortfolio(user);
		assertEquals(0, response.getResponse());
		assertEquals(ControllerConstants.EMPTY_PORTFOLIO, response.getData());
	}
	
	public void getUserPortfolioDateOK() {
		PortfolioRequestForDateDTO request = new PortfolioRequestForDateDTO();
		request.setId(new Long(35));
		request.setDate("2017-03-07");
		GenericResponse<?> response = this.portfolioCtrl.getUserPortfolioDate(request);
		PortfolioDTO portfolio = (PortfolioDTO) response.getData();
		assertEquals(1, response.getResponse());
		assertEquals(new Long(35), portfolio.getIdUser());
		assertEquals("2017-03-07", portfolio.getDate());
		assertEquals(13, portfolio.getList().size());
	}
	
	@Test
	public void getUserPortfolioDateProblem() {
		PortfolioRequestForDateDTO request = new PortfolioRequestForDateDTO();
		request.setId(new Long(15));
		request.setDate("2017-03-07");
		GenericResponse<?> response = this.portfolioCtrl.getUserPortfolioDate(request);
		assertEquals(0, response.getResponse());
		assertEquals(ControllerConstants.EMPTY_PORTFOLIO, response.getData());
	}
	
}
