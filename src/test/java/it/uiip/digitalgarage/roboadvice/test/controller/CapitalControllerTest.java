package it.uiip.digitalgarage.roboadvice.test.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.CapitalOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.CapitalController;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CapitalControllerTest {

    @Autowired
    private CapitalController capitalCtrl;

    @InjectMocks
    @Autowired
    private CapitalOperator capitalOp;

    @Mock
    private CapitalRepository capitalRep;

    @Mock
    private PortfolioRepository portfolioRep;

    @Mock
    private UserRepository userRep;

    @Mock
    private FinancialDataRepository financialDataRep;

    private static UserEntity user;
    private static Authentication auth;
    private static FinancialDataEntity financialDataEntity1;
    private static FinancialDataEntity financialDataEntity2;
    private static AssetClassEntity assetClassEntity1;
    private static AssetClassEntity assetClassEntity2;
    private static AssetEntity assetEntity1;
    private static AssetEntity assetEntity2;
    private static Map<Long, FinancialDataEntity> mapFD;

    @BeforeClass
    public static void setUpData() {
        user = new UserEntity();
        user.setId(2L);
        user.setEmail("luca@antilici.it");
        user.setPassword("pippo123");
        user.setDate(LocalDate.now());
        user.setLastUpdate(LocalDate.now().minusDays(1));

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        String role = "USER";
        authorities.add(new SimpleGrantedAuthority(role));
        User principal = new User("luca@antilici.it", "", authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        auth = SecurityContextHolder.getContext().getAuthentication();

        assetClassEntity1 = new AssetClassEntity();
        assetClassEntity1.setId(1L);
        assetClassEntity1.setName("bonds");
        assetClassEntity2 = new AssetClassEntity();
        assetClassEntity2.setId(3L);
        assetClassEntity2.setName("stocks");
        assetEntity1 = new AssetEntity();
        assetEntity1.setId(2L);
        assetEntity1.setAssetClass(assetClassEntity1);
        assetEntity1.setName("Ultra U.S. Treasury Bond Futures");
        assetEntity1.setDataSource("CHRIS/CME_UL1");
        assetEntity1.setPercentage(new BigDecimal(20.00));
        assetEntity1.setRemarksIndex(1);
        assetEntity1.setLastUpdate(LocalDate.now().minusDays(1));
        assetEntity2 = new AssetEntity();
        assetEntity2.setId(8L);
        assetEntity2.setAssetClass(assetClassEntity2);
        assetEntity2.setName("Microsoft");
        assetEntity2.setDataSource("WIKI/MSFT");
        assetEntity2.setPercentage(new BigDecimal(35.00));
        assetEntity2.setRemarksIndex(11);
        assetEntity2.setLastUpdate(LocalDate.now().minusDays(1));

        financialDataEntity1 = new FinancialDataEntity();
        financialDataEntity1.setId(1L);
        financialDataEntity1.setAsset(assetEntity1);
        financialDataEntity1.setValue(new BigDecimal(150.09));
        financialDataEntity1.setDate(LocalDate.now().minusDays(1));
        financialDataEntity2 = new FinancialDataEntity();
        financialDataEntity2.setId(1L);
        financialDataEntity2.setAsset(assetEntity2);
        financialDataEntity2.setValue(new BigDecimal(50.23));
        financialDataEntity2.setDate(LocalDate.now().minusDays(1));

        mapFD = new HashMap<>();
        mapFD.put(assetEntity1.getId(), financialDataEntity1);
        mapFD.put(assetEntity2.getId(), financialDataEntity2);
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
    public void addCapitalTestUpdateSameDayCapital() {
        CapitalRequestDTO capitalRequestDTO = new CapitalRequestDTO();
        capitalRequestDTO.setAmount(new BigDecimal("10123.4545"));

        CapitalEntity oldCapital = new CapitalEntity();
        oldCapital.setId(null);
        oldCapital.setUser(user);
        oldCapital.setAmount(new BigDecimal("13000.56"));
        oldCapital.setDate(LocalDate.now());

        CapitalEntity updatedCapital = new CapitalEntity();
        updatedCapital.setId(null);
        updatedCapital.setDate(LocalDate.now());
        updatedCapital.setUser(user);
        updatedCapital.setAmount(new BigDecimal("23124.0145"));

        when(capitalRep.findByUserAndDate(user, LocalDate.now())).thenReturn(oldCapital);
        when(capitalRep.save(updatedCapital)).thenReturn(updatedCapital);

        GenericResponse<String> ctrlResponse = (GenericResponse<String>) this.capitalCtrl.addCapital(capitalRequestDTO, auth);
        assertEquals(1, ctrlResponse.getResponse());
        assertEquals(ControllerConstants.DONE, ctrlResponse.getData());

    }


}
