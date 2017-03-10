package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.DefaultStrategyController;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
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
public class DefaultStrategyControllerTest {

    @Autowired
    private DefaultStrategyController defaultStrategyCtrl;
    @Test
    public void getDefaultStrategySetTestSuccess() {
        GenericResponse<?> response = this.defaultStrategyCtrl.getDefaultStrategySet();
        List<?> list = (List<?>) response.getData();
        DefaultStrategyDTO strategy = (DefaultStrategyDTO) list.get(0);
        assertEquals(1, response.getResponse());
        assertFalse(list.isEmpty());
        assertFalse(strategy.getList().isEmpty());
    }

}