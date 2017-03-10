package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
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
public class CapitalControllerTest {

    @Autowired
    private CapitalController capitalCtrl;

    @Test
    public void addCapital() throws Exception {

    }

    @Test
    public void getCurrentCapitalValidUser() throws Exception {
    	UserRegisteredDTO user = new UserRegisteredDTO();
        user.setId(new Long(23));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        GenericResponse<CapitalResponseDTO> response = (GenericResponse<CapitalResponseDTO>) this.capitalCtrl.getCurrentCapital(user);
        assertEquals(user.getId(), response.getData().getIdUser());
        assertEquals(1, response.getResponse());
        assertEquals(new BigDecimal(BigInteger.valueOf(0), 4), response.getData().getAmount());
        assertEquals("2017-03-09", response.getData().getDate());
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

    @Test
    public void computeCapital() throws Exception {

    }

    @Test
    public void getCapitalPeriodValidUserSingleDay() throws Exception {
        DataRequestDTO dto = new DataRequestDTO();
        dto.setId(new Long(23));
        dto.setPeriod(1);
        GenericResponse<List<CapitalResponseDTO>> response = (GenericResponse<List<CapitalResponseDTO>>) this.capitalCtrl.getCapitalPeriod(dto);
        assertEquals(dto.getId(), response.getData().get(0).getIdUser());
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData().size() == 1);
        assertEquals("2017-03-09", response.getData().get(0).getDate());
    }

    @Test
    public void getCapitalPeriodValidUserMoreDays() throws Exception {
        DataRequestDTO dto = new DataRequestDTO();
        dto.setId(new Long(23));
        dto.setPeriod(5);
        GenericResponse<List<CapitalResponseDTO>> response = (GenericResponse<List<CapitalResponseDTO>>) this.capitalCtrl.getCapitalPeriod(dto);
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData().size() > 1);
        assertEquals(dto.getId(), response.getData().get(0).getIdUser());
        assertEquals("2017-03-09", response.getData().get(0).getDate());
        assertEquals(dto.getId(), response.getData().get(1).getIdUser());
        assertEquals("2017-03-07", response.getData().get(1).getDate());
    }

}