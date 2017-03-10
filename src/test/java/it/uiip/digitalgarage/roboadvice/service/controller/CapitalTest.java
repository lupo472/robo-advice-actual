package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.CapitalOperator;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CapitalTest {

    @Autowired
    private CapitalController capitalCtrl;

    @Autowired
    private CapitalOperator capitalOp;

    @Test
    public void getCurrentCapitalValidUser() throws Exception {
    	UserRegisteredDTO user = new UserRegisteredDTO();
        user.setId(new Long(1));
        user.setEmail("test@test.it");
        user.setPassword("testit");
        GenericResponse<?> response = this.capitalCtrl.getCurrentCapital(user);
        CapitalResponseDTO capital = (CapitalResponseDTO) response.getData();
        assertEquals(user.getId(), capital.getIdUser());
        assertEquals(1, response.getResponse());
        assertFalse(capital.getAmount() == null);
    }

    @Test
    public void getCurrentCapitalInvalidUSer() throws Exception {
    	UserRegisteredDTO user = new UserRegisteredDTO();
        user.setId(new Long(0));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        GenericResponse<String> response = (GenericResponse<String>) this.capitalCtrl.getCurrentCapital(user);
        assertEquals(0, response.getResponse());
        assertEquals(ControllerConstants.ANY_CAPITAL, response.getData());

    }

   /* @Test
    public void computeCapitalValidUser() throws Exception {
        UserRegisteredDTO user = new UserRegisteredDTO();
        user.setId(new Long(23));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        boolean response = this.capitalOp.computeCapital(user);
        assertFalse(response);
    }
*/
    @Test
    public void computeCapitalInvalidUser() throws Exception {
        UserRegisteredDTO user = new UserRegisteredDTO();
        user.setId(new Long(0));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        boolean response = this.capitalOp.computeCapital(user);
        assertFalse(response);
    }

    /*
    @Test
    public void getCapitalPeriodValidUserSingleDay() throws Exception {
        DataRequestDTO dto = new DataRequestDTO();
        dto.setId(new Long(23));
        dto.setPeriod(1);
        GenericResponse<List<CapitalResponseDTO>> response = (GenericResponse<List<CapitalResponseDTO>>) this.capitalCtrl.getCapitalPeriod(dto);
        assertEquals(dto.getId(), response.getData().get(0).getIdUser());
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData().size() == 1);
    }
    */

    @Test
    public void getCapitalPeriodValidUserAllDays() throws Exception {
        DataRequestDTO dto = new DataRequestDTO();
        dto.setId(new Long(1));
        dto.setPeriod(0);
        GenericResponse<List<CapitalResponseDTO>> response = (GenericResponse<List<CapitalResponseDTO>>) this.capitalCtrl.getCapitalPeriod(dto);
        assertEquals(dto.getId(), response.getData().get(0).getIdUser());
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData().size() > 1);
    }

    @Test
    public void getCapitalPeriodValidUserMoreDays() throws Exception {
        DataRequestDTO dto = new DataRequestDTO();
        dto.setId(new Long(1));
        dto.setPeriod(30);
        GenericResponse<List<CapitalResponseDTO>> response = (GenericResponse<List<CapitalResponseDTO>>) this.capitalCtrl.getCapitalPeriod(dto);
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData().size() > 1);
        assertEquals(dto.getId(), response.getData().get(0).getIdUser());
//        assertEquals("2017-03-07", response.getData().get(0).getDate());
//        assertEquals(dto.getId(), response.getData().get(1).getIdUser());
//        assertEquals("2017-03-09", response.getData().get(1).getDate());
    }

}