package it.uiip.digitalgarage.roboadvice.test.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.CustomStrategyOperator;
import it.uiip.digitalgarage.roboadvice.logic.wrapper.CustomStrategyWrapper;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.CustomStrategyController;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 22/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CustomStrategyControllerTest {

    @Autowired
    private CustomStrategyController customStrategyCtrl;

    @InjectMocks
    @Autowired
    private CustomStrategyOperator customStrategyOp;

    @Mock
    private CustomStrategyRepository customStrategyRep;

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
    public void setCustomStrategySuccess() {
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

        when(customStrategyRep.save(entityList)).thenReturn(entityList);
        GenericResponse<String> ctrlResponse = (GenericResponse<String>) this.customStrategyCtrl.setCustomStrategy(dto, auth);
        assertEquals(1, ctrlResponse.getResponse());
        assertEquals(ControllerConstants.DONE, ctrlResponse.getData());
    }

    @Test
    public void getActiveStrategySuccess() {
        List<CustomStrategyEntity> resultList = new ArrayList<>();
        CustomStrategyEntity customStrategyEntity1 = new CustomStrategyEntity();
        AssetClassEntity assetClassEntity1 = new AssetClassEntity();
        assetClassEntity1.setId(new Long(2));
        assetClassEntity1.setName("bonds");
        customStrategyEntity1.setId(new Long(1));
        customStrategyEntity1.setUser(user);
        customStrategyEntity1.setAssetClass(assetClassEntity1);
        customStrategyEntity1.setPercentage(new BigDecimal(55.50));
        customStrategyEntity1.setActive(true);
        customStrategyEntity1.setDate(LocalDate.now());
        CustomStrategyEntity customStrategyEntity2 = new CustomStrategyEntity();
        AssetClassEntity assetClassEntity2 = new AssetClassEntity();
        assetClassEntity2.setId(new Long(4));
        assetClassEntity2.setName("stocks");
        customStrategyEntity2.setId(new Long(2));
        customStrategyEntity2.setUser(user);
        customStrategyEntity2.setAssetClass(assetClassEntity2);
        customStrategyEntity2.setPercentage(new BigDecimal(44.50));
        customStrategyEntity2.setActive(true);
        customStrategyEntity2.setDate(LocalDate.now());
        resultList.add(customStrategyEntity1);
        resultList.add(customStrategyEntity2);

        when(customStrategyRep.findByUserAndActive(user,true)).thenReturn(resultList);
        GenericResponse<CustomStrategyResponseDTO> ctrlResponse = (GenericResponse<CustomStrategyResponseDTO>) this.customStrategyCtrl.getActiveStrategy(auth);
        assertEquals(1, ctrlResponse.getResponse());
        assertTrue(ctrlResponse.getData().isActive());
        assertEquals(LocalDate.now().toString(), ctrlResponse.getData().getDate());
        assertFalse(ctrlResponse.getData().getList().isEmpty());
    }

    @Test
    public void getCustomStrategySetCurrentDay() {
        LocalDate currentDay = LocalDate.now();

        AssetClassEntity assetClassEntity1 = new AssetClassEntity();
        assetClassEntity1.setId(new Long(2));
        assetClassEntity1.setName("bonds");
        AssetClassEntity assetClassEntity2 = new AssetClassEntity();
        assetClassEntity2.setId(new Long(4));
        assetClassEntity2.setName("stocks");

        CustomStrategyEntity customStrategyCurrentDate = new CustomStrategyEntity();
        customStrategyCurrentDate.setId(new Long(1));
        customStrategyCurrentDate.setUser(user);
        customStrategyCurrentDate.setAssetClass(assetClassEntity1);
        customStrategyCurrentDate.setPercentage(new BigDecimal(55.50));
        customStrategyCurrentDate.setActive(true);
        customStrategyCurrentDate.setDate(currentDay);
        CustomStrategyEntity customStrategyCurrentDate2 = new CustomStrategyEntity();
        customStrategyCurrentDate2.setId(new Long(2));
        customStrategyCurrentDate2.setUser(user);
        customStrategyCurrentDate2.setAssetClass(assetClassEntity2);
        customStrategyCurrentDate2.setPercentage(new BigDecimal(44.50));
        customStrategyCurrentDate2.setActive(true);
        customStrategyCurrentDate2.setDate(currentDay);

        List<CustomStrategyEntity> resultList = new ArrayList<>();
        resultList.add(customStrategyCurrentDate);
        resultList.add(customStrategyCurrentDate2);

        when(customStrategyRep.findByUserAndDateBetween(user, LocalDate.now(), LocalDate.now())).thenReturn(resultList);

        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setPeriod(1);
        GenericResponse<List<CustomStrategyResponseDTO>> ctrlResponse = (GenericResponse<List<CustomStrategyResponseDTO>>) this.customStrategyCtrl.getCustomStrategyHistory(periodDTO, auth);
        assertEquals(1, ctrlResponse.getResponse());
        assertEquals(2, ctrlResponse.getData().get(0).getList().size());
    }

    @Test
    public void getCustomStrategySetAllDays() {
        LocalDate date1 = LocalDate.now();
        List<CustomStrategyEntity> resultList = new ArrayList<>();
        CustomStrategyEntity customStrategyEntity1_1 = new CustomStrategyEntity();
        AssetClassEntity assetClassEntity1_1 = new AssetClassEntity();
        assetClassEntity1_1.setId(new Long(2));
        assetClassEntity1_1.setName("bonds");
        customStrategyEntity1_1.setId(new Long(1));
        customStrategyEntity1_1.setUser(user);
        customStrategyEntity1_1.setAssetClass(assetClassEntity1_1);
        customStrategyEntity1_1.setPercentage(new BigDecimal(55.50));
        customStrategyEntity1_1.setActive(true);
        customStrategyEntity1_1.setDate(date1);
        CustomStrategyEntity customStrategyEntity1_2 = new CustomStrategyEntity();
        AssetClassEntity assetClassEntity1_2 = new AssetClassEntity();
        assetClassEntity1_2.setId(new Long(4));
        assetClassEntity1_2.setName("stocks");
        customStrategyEntity1_2.setId(new Long(2));
        customStrategyEntity1_2.setUser(user);
        customStrategyEntity1_2.setAssetClass(assetClassEntity1_2);
        customStrategyEntity1_2.setPercentage(new BigDecimal(44.50));
        customStrategyEntity1_2.setActive(true);
        customStrategyEntity1_2.setDate(date1);
        resultList.add(customStrategyEntity1_1);
        resultList.add(customStrategyEntity1_2);

        LocalDate date2 = LocalDate.now().minusDays(2);
        CustomStrategyEntity customStrategyEntity2_1 = new CustomStrategyEntity();
        AssetClassEntity assetClassEntity2_1 = new AssetClassEntity();
        assetClassEntity2_1.setId(new Long(2));
        assetClassEntity2_1.setName("forex");
        customStrategyEntity2_1.setId(new Long(1));
        customStrategyEntity2_1.setUser(user);
        customStrategyEntity2_1.setAssetClass(assetClassEntity2_1);
        customStrategyEntity2_1.setPercentage(new BigDecimal(20));
        customStrategyEntity2_1.setActive(false);
        customStrategyEntity2_1.setDate(date2);
        CustomStrategyEntity customStrategyEntity2_2 = new CustomStrategyEntity();
        AssetClassEntity assetClassEntity2_2 = new AssetClassEntity();
        assetClassEntity2_2.setId(new Long(4));
        assetClassEntity2_2.setName("stocks");
        customStrategyEntity2_2.setId(new Long(2));
        customStrategyEntity2_2.setUser(user);
        customStrategyEntity2_2.setAssetClass(assetClassEntity2_2);
        customStrategyEntity2_2.setPercentage(new BigDecimal(80));
        customStrategyEntity2_2.setActive(false);
        customStrategyEntity2_2.setDate(date2);
        resultList.add(customStrategyEntity2_1);
        resultList.add(customStrategyEntity2_2);

        when(customStrategyRep.findByUser(user)).thenReturn(resultList);
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setPeriod(0);
        GenericResponse<List<CustomStrategyResponseDTO>> ctrlResponse = (GenericResponse<List<CustomStrategyResponseDTO>>) this.customStrategyCtrl.getCustomStrategyHistory(periodDTO, auth);
        assertEquals(1, ctrlResponse.getResponse());
        assertEquals(2, ctrlResponse.getData().size());
        assertEquals(2, ctrlResponse.getData().get(0).getList().size());
        assertEquals(2, ctrlResponse.getData().get(1).getList().size());
    }

}
