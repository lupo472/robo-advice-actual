package it.uiip.digitalgarage.roboadvice.test.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.DefaultStrategyOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.DefaultStrategyController;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 22/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class DefaultStrategyControllerTest {

    @Autowired
    private DefaultStrategyController defaultStrategyCtrl;

    @InjectMocks
    @Autowired
    private DefaultStrategyOperator defaultStrategyOp;

    @Mock
    private DefaultStrategyRepository defaultStrategyRep;

    @Mock
    private UserRepository userRep;

    private static UserEntity user;
    private static Authentication auth;

    @BeforeClass
    public static void setUpAuth() {
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
        auth = SecurityContextHolder.getContext().getAuthentication();
    }

    @AfterClass
    public static void detachResources() {
        SecurityContextHolder.clearContext();
    }

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
        when(userRep.findByEmail("luca@antilici.it")).thenReturn(user);
    }


    @Test
    public void getDefaultStrategySetTestSuccess() {
        List<DefaultStrategyEntity> resultList = new ArrayList<>();
        AssetClassEntity assetClassEntity1 = new AssetClassEntity();
        assetClassEntity1.setId(2L);
        assetClassEntity1.setName("bonds");
        AssetClassEntity assetClassEntity2 = new AssetClassEntity();
        assetClassEntity2.setId(2L);
        assetClassEntity2.setName("stocks");

        DefaultStrategyEntity defaultStrategyRisky = new DefaultStrategyEntity();
        defaultStrategyRisky.setId(1L);
        defaultStrategyRisky.setName("risky");
        defaultStrategyRisky.setAssetClass(assetClassEntity1);
        defaultStrategyRisky.setPercentage(new BigDecimal(10));
        DefaultStrategyEntity defaultStrategyRisky2 = new DefaultStrategyEntity();
        defaultStrategyRisky2.setId(2L);
        defaultStrategyRisky2.setName("risky");
        defaultStrategyRisky2.setAssetClass(assetClassEntity2);
        defaultStrategyRisky2.setPercentage(new BigDecimal(90));

        DefaultStrategyEntity defaultStrategySafe = new DefaultStrategyEntity();
        defaultStrategySafe.setId(3L);
        defaultStrategySafe.setName("safe");
        defaultStrategySafe.setAssetClass(assetClassEntity1);
        defaultStrategySafe.setPercentage(new BigDecimal(90));
        DefaultStrategyEntity defaultStrategySafe2 = new DefaultStrategyEntity();
        defaultStrategySafe2.setId(3L);
        defaultStrategySafe2.setName("safe");
        defaultStrategySafe2.setAssetClass(assetClassEntity2);
        defaultStrategySafe2.setPercentage(new BigDecimal(10));

        List<DefaultStrategyEntity> defaultStrategyEntities = new ArrayList<>();
        defaultStrategyEntities.add(defaultStrategyRisky);
        defaultStrategyEntities.add(defaultStrategyRisky2);
        defaultStrategyEntities.add(defaultStrategySafe);
        defaultStrategyEntities.add(defaultStrategySafe2);

        when(defaultStrategyRep.findAll()).thenReturn(defaultStrategyEntities);
        GenericResponse<List<DefaultStrategyDTO>> ctrlResponse = (GenericResponse<List<DefaultStrategyDTO>>) this.defaultStrategyCtrl.getDefaultStrategySet();
        assertEquals(2, ctrlResponse.getData().size());
        assertEquals(2, ctrlResponse.getData().get(0).getList().size());
        assertEquals(2, ctrlResponse.getData().get(1).getList().size());
        assertEquals("risky", ctrlResponse.getData().get(0).getName());
        assertEquals("safe", ctrlResponse.getData().get(1).getName());
    }
}
