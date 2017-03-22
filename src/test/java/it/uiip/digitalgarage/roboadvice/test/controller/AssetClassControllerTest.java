package it.uiip.digitalgarage.roboadvice.test.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.AssetClassOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.AssetClassController;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 22/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class AssetClassControllerTest {

    @Autowired
    public AssetClassController assetClassController;

    @InjectMocks
    @Autowired
    private AssetClassOperator assetClassOp;

    @Mock
    private AssetClassRepository assetClassRep;

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAssetClassSetSuccess() {
        List<AssetClassEntity> resultList = new ArrayList<>();
        AssetClassEntity assetClass1 = new AssetClassEntity();
        assetClass1.setId(new Long(1));
        assetClass1.setName("bonds");
        AssetClassEntity assetClass2 = new AssetClassEntity();
        assetClass2.setId(new Long(2));
        assetClass2.setName("forex");
        AssetClassEntity assetClass3 = new AssetClassEntity();
        assetClass3.setId(new Long(3));
        assetClass3.setName("stocks");
        AssetClassEntity assetClass4 = new AssetClassEntity();
        assetClass4.setId(new Long(4));
        assetClass4.setName("commodities");
        AssetClassEntity assetClass5 = new AssetClassEntity();
        assetClass5.setId(new Long(5));
        assetClass5.setName("dummy");
        resultList.add(assetClass1);
        resultList.add(assetClass2);
        resultList.add(assetClass3);
        resultList.add(assetClass4);
        resultList.add(assetClass5);
        assertNotNull(assetClassRep);
        when(assetClassRep.findAll()).thenReturn(resultList);

        GenericResponse<List<AssetClassDTO>> controllerResponse = (GenericResponse<List<AssetClassDTO>>) assetClassController.getAssetClassSet();
        assertTrue(controllerResponse.getData().size() > 0);
        assertEquals(new Long(1), controllerResponse.getData().get(0).getId());
        assertEquals(new Long(2), controllerResponse.getData().get(1).getId());
        assertEquals(new Long(3), controllerResponse.getData().get(2).getId());
        assertEquals(new Long(4), controllerResponse.getData().get(3).getId());
        assertEquals(new Long(5), controllerResponse.getData().get(4).getId());
        assertEquals("bonds", controllerResponse.getData().get(0).getName());
        assertEquals("forex", controllerResponse.getData().get(1).getName());
        assertEquals("stocks", controllerResponse.getData().get(2).getName());
        assertEquals("commodities", controllerResponse.getData().get(3).getName());
        assertEquals("dummy", controllerResponse.getData().get(4).getName());
    }
}
