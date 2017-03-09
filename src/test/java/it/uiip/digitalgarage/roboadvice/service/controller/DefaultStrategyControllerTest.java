package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
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
public class DefaultStrategyControllerTest {

    @Autowired
    private DefaultStrategyController defaultStrategyCtrl;
    @Test
    public void getDefaultStrategySetTestSuccess() throws Exception {
        GenericResponse<List<DefaultStrategyDTO>> response = (GenericResponse<List<DefaultStrategyDTO>>) this.defaultStrategyCtrl.getDefaultStrategySet();
        assertEquals(1, response.getResponse());
        assertFalse(response.getData().isEmpty());

        assertEquals("income", response.getData().get(0).getName());
        assertFalse(response.getData().get(0).getList().isEmpty());
        assertEquals("bonds", response.getData().get(1).getName());
    }

}