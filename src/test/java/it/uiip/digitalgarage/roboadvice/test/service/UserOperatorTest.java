package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.logic.operator.UserOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
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
import it.uiip.digitalgarage.roboadvice.service.controller.UserController;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class UserOperatorTest {

	@InjectMocks
	@Autowired
	private UserOperator userOp;

	@Mock
	private UserRepository userRep;

	private static UserDTO userDTO;

	@BeforeClass
	public static void setUpData() {
		userDTO = new UserDTO();
		userDTO.setEmail("test@test.com");
		userDTO.setPassword("123456");
	}

	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loginUserTestUserNotExists() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(null);
		userEntity.setEmail("test@test.com");
		userEntity.setPassword("3n4kjb");
		userEntity.setDate(LocalDate.now());
		userEntity.setLastUpdate(LocalDate.now().minusDays(1));

		when(userRep.findByEmail(userDTO.getEmail())).thenReturn(userEntity);
		LoginDTO response = userOp.loginUser(userDTO);
		assertNull(response);
	}

	@Test
	public void registerUserTestUserAlreadyRegistered() {
		when(userRep.findByEmail(userDTO.getEmail())).thenReturn(new UserEntity());
		assertFalse(userOp.registerUser(userDTO));

	}
}
