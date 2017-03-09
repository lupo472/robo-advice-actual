package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Before;
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
public class AssetControllerTest {

    @Autowired
    private AssetController assetCtrl;

    @Test
    public void getAssetSetTestSuccess() throws Exception {
        GenericResponse<List<AssetDTO>> response = (GenericResponse<List<AssetDTO>>) this.assetCtrl.getAssetSet();
        assertEquals(1,response.getResponse());
        assertFalse(response.getData().isEmpty());
    }

}