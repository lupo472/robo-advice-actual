package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import it.uiip.digitalgarage.roboadvice.service.controller.AssetClassController;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class AssetClassControllerTest {
	
    @Autowired
    private AssetClassController assetClassCtrl;

    @Test
    public void getAssetClassSetSuccess() {
        GenericResponse<?> response = this.assetClassCtrl.getAssetClassSet();
        List<?> assetClass = (List<?>) response.getData();
        assertEquals(1, response.getResponse());
        assertFalse(assetClass.isEmpty());
        assertTrue(assetClass.get(0) instanceof AssetClassDTO);
    }

}