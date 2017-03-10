package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.DefaultStrategyController;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
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
/*
        DefaultStrategyDTO dto = new DefaultStrategyDTO();
        List<AssetClassStrategyDTO> dtoList = new ArrayList<>();
        AssetClassStrategyDTO assetClassStrategy = new AssetClassStrategyDTO();
        AssetClassDTO assetClass = new AssetClassDTO();
        assetClass.setName("bonds");
        assetClass.setId(new Long(1));
        assetClassStrategy.setAssetClass(assetClass);
        assetClassStrategy.setPercentage(new BigDecimal(65));
        dtoList.add(assetClassStrategy);

        AssetClassStrategyDTO assetClassStrategy2 = new AssetClassStrategyDTO();
        AssetClassDTO assetClass2 = new AssetClassDTO();
        assetClass2.setName("stocks");
        assetClass2.setId(new Long(3));
        assetClassStrategy2.setAssetClass(assetClass2);
        assetClassStrategy2.setPercentage(new BigDecimal(10));
        dtoList.add(assetClassStrategy2);

        AssetClassStrategyDTO assetClassStrategy3 = new AssetClassStrategyDTO();
        AssetClassDTO assetClass3 = new AssetClassDTO();
        assetClass3.setName("forex");
        assetClass3.setId(new Long(2));
        assetClassStrategy3.setAssetClass(assetClass3);
        assetClassStrategy3.setPercentage(new BigDecimal(15));
        dtoList.add(assetClassStrategy3);

        AssetClassStrategyDTO assetClassStrategy4 = new AssetClassStrategyDTO();
        AssetClassDTO assetClass4 = new AssetClassDTO();
        assetClass4.setName("forex");
        assetClass4.setId(new Long(4));
        assetClassStrategy4.setAssetClass(assetClass4);
        assetClassStrategy4.setPercentage(new BigDecimal(10));
        dtoList.add(assetClassStrategy4);
        dto.setList(dtoList);
        dto.setName("income");

        assertTrue(response.getData().contains(dto));
*/

        assertEquals("income", response.getData().get(0).getName());
        assertFalse(response.getData().get(0).getList().isEmpty());
        assertEquals("bonds", response.getData().get(1).getName());
        assertFalse(response.getData().get(1).getList().isEmpty());
        assertEquals("growth", response.getData().get(2).getName());
        assertFalse(response.getData().get(2).getList().isEmpty());
        assertEquals("balanced", response.getData().get(3).getName());
        assertFalse(response.getData().get(3).getList().isEmpty());
        assertEquals("stocks", response.getData().get(4).getName());
        assertFalse(response.getData().get(4).getList().isEmpty());


    }

}