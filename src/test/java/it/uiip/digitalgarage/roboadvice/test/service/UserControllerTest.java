package it.uiip.digitalgarage.roboadvice.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.UserController;
import it.uiip.digitalgarage.roboadvice.service.dto.AuthDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

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
		assertTrue(response.getData() instanceof LoginDTO);
	}
	
	@Test
	public void loginTestOkInspect() {
		UserDTO user = new UserDTO();
		user.setEmail("cristian.laurini@gmail.com");
		user.setPassword("cristianlaurini");
		GenericResponse<?> response = userCtrl.loginUser(user);
		LoginDTO login = (LoginDTO) response.getData();
		UserRegisteredDTO userRegistered = login.getUser();
		AuthDTO auth = login.getAuth();
		assertEquals(new Long(35), auth.getId());
		assertEquals(new Long(35), userRegistered.getId());
		assertTrue(auth.getToken() instanceof String);
		assertEquals(HashFunction.hashStringSHA256("cristianlaurini"), userRegistered.getPassword());
		assertEquals("cristian.laurini@gmail.com", userRegistered.getEmail());
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
