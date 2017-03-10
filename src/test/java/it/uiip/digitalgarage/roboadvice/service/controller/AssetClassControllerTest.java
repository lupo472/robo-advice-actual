package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Luca on 10/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class AssetClassControllerTest {

    @Autowired
    private AssetClassController assetClassCtrl;

    @Test
    public void getAssetClassSetSuccess() throws Exception {
        GenericResponse<?> response = this.assetClassCtrl.getAssetClassSet();
        List<AssetClassDTO> assetClass = (List<AssetClassDTO>) response.getData();
        assertEquals(1, response.getResponse());
        assertFalse(assetClass.isEmpty());
    }

}