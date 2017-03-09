package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Luca on 08/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CustomStrategyControllerTest {

    @Autowired
    private CustomStrategyController customStrategyCtrl;
    @Test
    public void setCustomStrategy() throws Exception {

    }

    @Test
    public void getUserCustomStrategySet() throws Exception {
        /*UserLoggedDTO user = new UserLoggedDTO();
        user.setId(new Long(23));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        GenericResponse<List<CustomStrategyResponseDTO>> response = (GenericResponse<List<CustomStrategyResponseDTO>>) this.customStrategyCtrl.getUserCustomStrategySet(user);
        assertEquals(1,response.getResponse());
        assertFalse(response.getData().isEmpty());
*/
    }

    @Test
    public void getUserCustomStrategyActive() throws Exception {

    }

}