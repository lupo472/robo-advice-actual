package it.uiip.digitalgarage.roboadvice.service.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.UserController;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class UserControllerTest {

	@Autowired
	private UserController userCtrl;
	
	@Test
	public void loginTestOK() {
		UserDTO user = new UserDTO();
		user.setEmail("cristian.laurini@gmail.com");
		user.setPassword("cristianlaurini");
		GenericResponse<?> response = userCtrl.loginUser(user);
		assertEquals(1, response.getResponse());
	}
	
	@Test
	public void loginTestWrongPassword() {
		UserDTO user = new UserDTO();
		user.setEmail("cristian.laurini@gmail.com");
		user.setPassword("cristian");
		GenericResponse<?> response = userCtrl.loginUser(user);
		assertEquals(0, response.getResponse());
		assertEquals(ControllerConstants.WRONG_PASSWORD, response.getData());
	}
	
	@Test
	public void loginTestEmailNotRegistered() {
		UserDTO user = new UserDTO();
		user.setEmail("cristian@gmail.com");
		user.setPassword("cristianlaurini");
		GenericResponse<?> response = userCtrl.loginUser(user);
		assertEquals(0, response.getResponse());
		assertEquals(ControllerConstants.EMAIL_NOT_REGISTERED, response.getData());
	}
	
	@Test
	public void registerTestEmailAlreadyRegistered() {
		UserDTO user = new UserDTO();
		user.setEmail("cristian.laurini@gmail.com");
		user.setPassword("cristianlaurini");
		GenericResponse<?> response = userCtrl.registerUser(user);
		assertEquals(0, response.getResponse());
		assertEquals(ControllerConstants.EMAIL_ALREADY_REGISTERED, response.getData());
	}
	
	@Test
	public void registerTestOK() {
		UserDTO user = new UserDTO();
		LocalDateTime time = LocalDateTime.now();
		int email = time.toString().hashCode();
		user.setEmail(email + "@test.it");
		user.setPassword("test");
		GenericResponse<?> response = userCtrl.registerUser(user);
		assertEquals(1, response.getResponse());
	}

}
