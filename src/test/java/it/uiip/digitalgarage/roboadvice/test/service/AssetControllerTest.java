package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.AssetController;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
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
public class AssetControllerTest {
	
    @Autowired
    private AssetController assetCtrl;

    @Test
    public void getAssetSetTestSuccess() {
        GenericResponse<?> response = this.assetCtrl.getAssetSet();
        List<?> list = (List<?>) response.getData();
        assertEquals(1,response.getResponse());
        assertFalse(list.isEmpty());
        assertTrue(list.get(0) instanceof AssetDTO);
    }

}