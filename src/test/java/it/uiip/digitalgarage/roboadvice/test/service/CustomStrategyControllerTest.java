package it.uiip.digitalgarage.roboadvice.test.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.CustomStrategyOperator;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.CustomStrategyWrapper;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.CustomStrategyController;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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

    @InjectMocks
    @Autowired
    private CustomStrategyOperator customStrategyOp;

    @Mock
    private CustomStrategyRepository customStrategyRep;

    @Mock
    private UserRepository userRep;

    private UserEntity user;

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
        user = new UserEntity();
        user.setId(new Long(2));
        user.setEmail("luca@antilici.it");
        user.setPassword("pippo123");
        user.setDate(LocalDate.now());
        user.setLastUpdate(LocalDate.now());

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        String role = "USER";
        authorities.add(new SimpleGrantedAuthority(role));
        User principal = new User("luca@antilici.it", "", authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
    @Test
    public void setCustomStrategyValidUser() {
        /*CustomStrategyDTO dto = new CustomStrategyDTO();
        List<AssetClassStrategyDTO> dtoList = new ArrayList<>();
        AssetClassStrategyDTO assetClassStrategy = new AssetClassStrategyDTO();
        assetClassStrategy.setName("bonds");
        assetClassStrategy.setId(new Long(1));
        assetClassStrategy.setPercentage(new BigDecimal(40));
        dtoList.add(assetClassStrategy);
        AssetClassStrategyDTO assetClassStrategy2 = new AssetClassStrategyDTO();
        assetClassStrategy2.setName("stocks");
        assetClassStrategy2.setId(new Long(3));
        assetClassStrategy2.setPercentage(new BigDecimal(10));
        dtoList.add(assetClassStrategy2);
        AssetClassStrategyDTO assetClassStrategy3 = new AssetClassStrategyDTO();
        assetClassStrategy3.setName("forex");
        assetClassStrategy3.setId(new Long(2));
        assetClassStrategy3.setPercentage(new BigDecimal(10));
        dtoList.add(assetClassStrategy3);
        AssetClassStrategyDTO assetClassStrategy4 = new AssetClassStrategyDTO();
        assetClassStrategy4.setName("commodities");
        assetClassStrategy4.setId(new Long(4));
        assetClassStrategy4.setPercentage(new BigDecimal(40));
        dtoList.add(assetClassStrategy4);
        dto.setList(dtoList);
        dto.setIdUser(new Long(23));
        GenericResponse<?> response = this.customStrategyCtrl.setCustomStrategy(dto);
        assertEquals(1, response.getResponse());*/

        CustomStrategyDTO dto = new CustomStrategyDTO();
        List<AssetClassStrategyDTO> dtoList = new ArrayList<>();
        AssetClassStrategyDTO assetClassStrategy = new AssetClassStrategyDTO();
        assetClassStrategy.setName("bonds");
        assetClassStrategy.setId(new Long(1));
        assetClassStrategy.setPercentage(new BigDecimal(40));
        dtoList.add(assetClassStrategy);
        AssetClassStrategyDTO assetClassStrategy2 = new AssetClassStrategyDTO();
        assetClassStrategy2.setName("stocks");
        assetClassStrategy2.setId(new Long(3));
        assetClassStrategy2.setPercentage(new BigDecimal(10));
        dtoList.add(assetClassStrategy2);
        AssetClassStrategyDTO assetClassStrategy3 = new AssetClassStrategyDTO();
        assetClassStrategy3.setName("forex");
        assetClassStrategy3.setId(new Long(2));
        assetClassStrategy3.setPercentage(new BigDecimal(10));
        dtoList.add(assetClassStrategy3);
        AssetClassStrategyDTO assetClassStrategy4 = new AssetClassStrategyDTO();
        assetClassStrategy4.setName("commodities");
        assetClassStrategy4.setId(new Long(4));
        assetClassStrategy4.setPercentage(new BigDecimal(40));
        dtoList.add(assetClassStrategy4);
        dto.setList(dtoList);
        CustomStrategyWrapper wrapper = new CustomStrategyWrapper();
        List<CustomStrategyEntity> entityList = wrapper.unwrapToEntity(dto);

        when(userRep.findByEmail("luca@antilici.it")).thenReturn(user);
        when(customStrategyRep.save(entityList)).thenReturn(entityList);
        GenericResponse<?> response = this.customStrategyCtrl.setCustomStrategy(dto, SecurityContextHolder.getContext().getAuthentication());
        assertEquals(1, response.getResponse());


    }
    @After
    public void detachResources() {
        SecurityContextHolder.clearContext();
        System.out.println("Cleared context");
    }
/*

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
*/

}