package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.CapitalController;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CapitalControllerTest {

	/* TODO
	 * Test: addCapital
	 * Test: getCurrentCapital
	 * Test: computeCapital
	 * Test: getCapitalPeriod
	*/

    @Autowired
    private CapitalController capitalCtrl;

//    @Test
//    public void getCurrentCapitalValidUser() {
//    	UserRegisteredDTO user = new UserRegisteredDTO();
//        user.setId(new Long(1));
//        user.setEmail("test@test.it");
//        user.setPassword("testit");
//        GenericResponse<?> response = this.capitalCtrl.getCurrentCapital(user);
//        CapitalResponseDTO capital = (CapitalResponseDTO) response.getData();
//        assertEquals(user.getId(), capital.getIdUser());
//        assertEquals(1, response.getResponse());
//        assertFalse(capital.getAmount() == null);
//    }
//
//    @Test
//    public void getCurrentCapitalInvalidUser() {
//    	UserRegisteredDTO user = new UserRegisteredDTO();
//        user.setId(new Long(0));
//        user.setEmail("test@case.it");
//        user.setPassword("12345");
//        GenericResponse<?> response = this.capitalCtrl.getCurrentCapital(user);
//        assertEquals(0, response.getResponse());
//        assertEquals(ControllerConstants.ANY_CAPITAL, response.getData());
//    }
//
//    @Test
//    public void computeCapitalInvalidUser() {
//        UserRegisteredDTO user = new UserRegisteredDTO();
//        user.setId(new Long(0));
//        user.setEmail("test@case.it");
//        user.setPassword("12345");
//        GenericResponse<?> response = this.capitalCtrl.computeCapital(user);
//        assertTrue(response.getResponse() == 0);
//    }
//
//    @Test
//    public void getCapitalPeriodValidUserAllDays() {
//        DataRequestDTO dto = new DataRequestDTO();
//        dto.setId(new Long(1));
//        dto.setPeriod(0);
//        GenericResponse<?> response = this.capitalCtrl.getCapitalPeriod(dto);
//        List<?> list = (List<?>) response.getData();
//        CapitalResponseDTO capital = (CapitalResponseDTO) list.get(0);
//        assertEquals(dto.getId(), capital.getIdUser());
//        assertFalse(list.isEmpty());
//        assertTrue(list.size() > 1);
//    }
//
//    @Test
//    public void getCapitalPeriodValidUserMoreDays() {
//        DataRequestDTO dto = new DataRequestDTO();
//        dto.setId(new Long(1));
//        dto.setPeriod(30);
//        GenericResponse<?> response = this.capitalCtrl.getCapitalPeriod(dto);
//        List<?> list = (List<?>) response.getData();
//        CapitalResponseDTO capital = (CapitalResponseDTO) list.get(0);
//        assertFalse(list.isEmpty());
//        assertTrue(list.size() > 1);
//        assertEquals(dto.getId(), capital.getIdUser());
//    }

}