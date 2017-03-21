package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.AssetClassOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import it.uiip.digitalgarage.roboadvice.service.controller.AssetClassController;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class AssetClassOperatorTest {

    @Autowired
    public AssetClassController assetClassController;

    @InjectMocks
    @Autowired
    private AssetClassOperator assetClassOp;

    @Mock
    private AssetClassRepository assetClassRep;

    private AssetClassDTO assetClassDTO;
    private AssetClassEntity assetClassEntity;

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
        //assetClassDTO = mock(AssetClassDTO.class);
        //assetClassEntity = mock(AssetClassEntity.class);
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
        List<AssetClassDTO> serviceList = assetClassOp.getAssetClassSet();
        assertEquals(5, assetClassRep.findAll().size());
        assertEquals(5, serviceList.size());
        assertEquals(new Long(1), serviceList.get(0).getId());
        assertEquals(new Long(2), serviceList.get(1).getId());
        assertEquals(new Long(3), serviceList.get(2).getId());
        assertEquals(new Long(4), serviceList.get(3).getId());
        assertEquals(new Long(5), serviceList.get(4).getId());
        assertEquals("bonds", serviceList.get(0).getName());
        assertEquals("forex", serviceList.get(1).getName());
        assertEquals("stocks", serviceList.get(2).getName());
        assertEquals("commodities", serviceList.get(3).getName());
        assertEquals("dummy", serviceList.get(4).getName());
        GenericResponse<List<AssetClassDTO>> controllerResponse = (GenericResponse<List<AssetClassDTO>>) assetClassController.getAssetClassSet();
        assertTrue(controllerResponse.getData().size() > 0);

    }

}