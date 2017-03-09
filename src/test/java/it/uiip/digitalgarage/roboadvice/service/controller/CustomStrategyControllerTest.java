package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.net.www.content.text.Generic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Luca on 08/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CustomStrategyControllerTest {

    @Autowired
    private CustomStrategyController customStrategyCtrl;

    @Test
    public void setCustomStrategyValidUser() throws Exception {

        CustomStrategyDTO dto = new CustomStrategyDTO();
        List<AssetClassStrategyDTO> dtoList = new ArrayList<>();
        AssetClassStrategyDTO assetClassStrategy = new AssetClassStrategyDTO();
        AssetClassDTO assetClass = new AssetClassDTO();
        assetClass.setName("bonds");
        assetClass.setId(new Long(1));
        assetClassStrategy.setAssetClass(assetClass);
        assetClassStrategy.setPercentage(new BigDecimal(40));
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
        assetClassStrategy3.setPercentage(new BigDecimal(10));
        dtoList.add(assetClassStrategy3);

        AssetClassStrategyDTO assetClassStrategy4 = new AssetClassStrategyDTO();
        AssetClassDTO assetClass4 = new AssetClassDTO();
        assetClass4.setName("commodities");
        assetClass4.setId(new Long(4));
        assetClassStrategy4.setAssetClass(assetClass4);
        assetClassStrategy4.setPercentage(new BigDecimal(40));
        dtoList.add(assetClassStrategy4);
        dto.setList(dtoList);
        dto.setIdUser(new Long(23));

        GenericResponse<String> response = (GenericResponse<String>) this.customStrategyCtrl.setCustomStrategy(dto);
        assertEquals(1, response.getResponse());
    }

    @Test
    public void setCustomStrategyInvalidUser() throws Exception {
        CustomStrategyDTO dto = new CustomStrategyDTO();
        dto.setIdUser(new Long(999999999));
        GenericResponse<String> response = (GenericResponse<String>) this.customStrategyCtrl.setCustomStrategy(dto);
        assertEquals(0,response.getResponse());
        assertEquals("A problem occurred", response.getData());
    }

    @Test
    public void getUserCustomStrategySetValidUser() throws Exception {
        UserLoggedDTO user = new UserLoggedDTO();
        user.setId(new Long(23));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        GenericResponse<List<CustomStrategyResponseDTO>> response = (GenericResponse<List<CustomStrategyResponseDTO>>) this.customStrategyCtrl.getUserCustomStrategySet(user);
        assertEquals(1,response.getResponse());
        assertFalse(response.getData().isEmpty());
    }

    @Test
    public void getUserCustomStrategySetInvalidUser() throws Exception {
        UserLoggedDTO user = new UserLoggedDTO();
        user.setId(new Long(999999999));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        GenericResponse<String> response = (GenericResponse<String>) this.customStrategyCtrl.getUserCustomStrategySet(user);
        assertEquals("This user doesn't have any strategy", response.getData());
        assertEquals(0, response.getResponse());
    }

    @Test
    public void getUserCustomStrategyActiveValidUser() throws Exception {
        UserLoggedDTO dto = new UserLoggedDTO();
        dto.setId(new Long(23));
        dto.setEmail("email@email");
        dto.setPassword("12345");
        GenericResponse<CustomStrategyResponseDTO> response = (GenericResponse<CustomStrategyResponseDTO>) this.customStrategyCtrl.getUserCustomStrategyActive(dto);
        assertEquals(1, response.getResponse());
        assertFalse(response.getData() == null);
    }

    @Test
    public void getUserCustomStrategyActiveInvalidUser() throws Exception {
        UserLoggedDTO dto = new UserLoggedDTO();
        dto.setId(new Long(999999999));
        dto.setEmail("email@email");
        dto.setPassword("12345");
        GenericResponse<String> response = (GenericResponse<String>) this.customStrategyCtrl.getUserCustomStrategyActive(dto);
        assertEquals(0, response.getResponse());
        assertEquals("This user doesn't have any active strategy", response.getData());
    }

}