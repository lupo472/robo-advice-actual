package com.roboadvice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.PortfolioController;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class PortofolioControllerTest {

	@Autowired
	private PortfolioController portfolioCtrl;
	
	@Test
	public void getUserCurrentPortfolioOK() {
		UserLoggedDTO user = new UserLoggedDTO();
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
		UserLoggedDTO user = new UserLoggedDTO();
		user.setId(new Long(15));
		user.setEmail("criidd@a");
		user.setPassword("ceaaaaaa");
		GenericResponse<?> response = this.portfolioCtrl.getUserCurrentPortfolio(user);
		assertEquals(0, response.getResponse());
		assertEquals("The portfolio of this user is empty", response.getData());
	}

	
	
}
