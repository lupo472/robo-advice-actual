package it.uiip.digitalgarage.roboadvice.test.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.UserOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.UserController;
import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;
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

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 22/03/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class UserControllerTest {

    @Autowired
    private UserController userCtrl;

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
    public void loginUserSuccess() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setEmail("test@test.com");
        String hashedPassword = HashFunction.hashStringSHA256("123456");
        userEntity.setPassword(hashedPassword);
        userEntity.setDate(LocalDate.now());
        userEntity.setLastUpdate(LocalDate.now().minusDays(1));


        when(userRep.findByEmail(userDTO.getEmail())).thenReturn(userEntity);
        GenericResponse<LoginDTO> ctrlResponse = (GenericResponse<LoginDTO>) userCtrl.loginUser(userDTO);
        assertEquals(1, ctrlResponse.getResponse());
        assertEquals(userEntity.getEmail(), ctrlResponse.getData().getEmail());
        assertNotNull(ctrlResponse.getData().getToken());
    }

    @Test
    public void loginUserTestUserNotExists() {
        when(userRep.findByEmail(userDTO.getEmail())).thenReturn(null);
        GenericResponse<String> ctrlResponse = (GenericResponse<String>) userCtrl.loginUser(userDTO);
        assertEquals(0, ctrlResponse.getResponse());
        assertEquals(ControllerConstants.EMAIL_NOT_REGISTERED, ctrlResponse.getData());
    }

    @Test
    public void registerUserTestUserAlreadyRegistered() {
        when(userRep.findByEmail(userDTO.getEmail())).thenReturn(new UserEntity());
        assertFalse(userOp.registerUser(userDTO));

    }

}
