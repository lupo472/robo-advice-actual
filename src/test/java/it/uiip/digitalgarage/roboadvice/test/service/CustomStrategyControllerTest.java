package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.controller.CustomStrategyController;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CustomStrategyControllerTest {

	/* TODO
	 * Test: setCustomStrategy
	 * Test: getUserCustomStrategySet
	 * Test: getActiveUserCustomStrategy
	*/
	
    @Autowired
    private CustomStrategyController customStrategyCtrl;

    @Test
    public void setCustomStrategyValidUser() {
        CustomStrategyDTO dto = new CustomStrategyDTO();
        List<AssetClassStrategyDTO> dtoList = new ArrayList<>();
        AssetClassStrategyDTO assetClassStrategy = new AssetClassStrategyDTO();
        assetClassStrategy.setName("bonds");
        assetClassStrategy.setId(new Long(1));
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
        GenericResponse<?> response = this.customStrategyCtrl.setCustomStrategy(dto);
        assertEquals(1, response.getResponse());
    }

    @Test
    public void setCustomStrategyInvalidUser() throws Exception {
        CustomStrategyDTO dto = new CustomStrategyDTO();
        dto.setIdUser(new Long(0));
        GenericResponse<?> response = this.customStrategyCtrl.setCustomStrategy(dto);
        assertEquals(0,response.getResponse());
        assertEquals(ControllerConstants.PROBLEM, response.getData());
    }

    @Test
    public void getUserCustomStrategySetValidUser() throws Exception {
        UserRegisteredDTO user = new UserRegisteredDTO();
        user.setId(new Long(23));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        GenericResponse<?> response = this.customStrategyCtrl.getUserCustomStrategySet(user);
        List<?> list = (List<?>) response.getData();
        assertEquals(1,response.getResponse());
        assertFalse(list.isEmpty());
    }

    @Test
    public void getUserCustomStrategySetInvalidUser() throws Exception {
    	UserRegisteredDTO user = new UserRegisteredDTO();
        user.setId(new Long(0));
        user.setEmail("test@case.it");
        user.setPassword("12345");
        GenericResponse<?> response = this.customStrategyCtrl.getUserCustomStrategySet(user);
        assertEquals(ControllerConstants.ANY_STRATEGY, response.getData());
        assertEquals(0, response.getResponse());
    }

    @Test
    public void getUserCustomStrategyActiveValidUser() throws Exception {
    	UserRegisteredDTO dto = new UserRegisteredDTO();
        dto.setId(new Long(35));
        dto.setEmail("cristian.laurini@gmail");
        dto.setPassword("cristianlaurini");
        GenericResponse<?> response = this.customStrategyCtrl.getUserCustomStrategyActive(dto);
        assertEquals(1, response.getResponse());
        assertFalse(response.getData() == null);
    }

    @Test
    public void getUserCustomStrategyActiveInvalidUser() throws Exception {
    	UserRegisteredDTO dto = new UserRegisteredDTO();
        dto.setId(new Long(0));
        dto.setEmail("email@email");
        dto.setPassword("12345");
        GenericResponse<?> response = this.customStrategyCtrl.getUserCustomStrategyActive(dto);
        assertEquals(0, response.getResponse());
        assertEquals(ControllerConstants.ANY_ACTIVE_STRATEGY, response.getData());
    }

}