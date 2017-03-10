package it.uiip.digitalgarage.roboadvice.test.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.AuthController;
import it.uiip.digitalgarage.roboadvice.service.controller.UserController;
import it.uiip.digitalgarage.roboadvice.service.dto.AuthDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class AuthControllerTest {

	@Autowired
	private AuthController authCtrl;
	
	@Autowired
	private UserController userCtrl;
	
	@Test
	public void authenticationOKTest() {
		UserDTO user = new UserDTO();
		user.setEmail("cristian.laurini@gmail.com");
		user.setPassword("cristianlaurini");
		GenericResponse<?> responseLogin = this.userCtrl.loginUser(user);
		LoginDTO login = (LoginDTO) responseLogin.getData();
		AuthDTO auth = login.getAuth();
		GenericResponse<?> response = this.authCtrl.authenticate(auth);
		System.out.println(response);
		assertEquals(1, response.getResponse());
		assertEquals(ControllerConstants.AUTHENTICATED, response.getData());
	}
	
	@Test
	public void authenticationFailTest() {
		AuthDTO auth = new AuthDTO();
		auth.setId(new Long(35));
		auth.setToken("fail");
		GenericResponse<?> response = this.authCtrl.authenticate(auth);
		assertEquals(0, response.getResponse());
		assertEquals(ControllerConstants.AUTHENTICATION_FAILED, response.getData());
	}

}
