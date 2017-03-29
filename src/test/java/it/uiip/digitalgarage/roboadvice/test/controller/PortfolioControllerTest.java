package it.uiip.digitalgarage.roboadvice.test.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Value;
import it.uiip.digitalgarage.roboadvice.service.controller.PortfolioController;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;
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
import sun.net.www.content.text.Generic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


/**
 * Created by Luca on 28/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class PortfolioControllerTest {

    @Autowired
    private PortfolioController portfolioCl;

    @InjectMocks
    @Autowired
    private PortfolioOperator portfolioOper;

    @Mock
    private PortfolioRepository portfolioRep;

    @Mock
    private UserRepository userRep;


    private static UserEntity user;
    private static AssetClassEntity assetClassEntity1;
    private static AssetEntity assetEntity1;
    private static AssetEntity assetEntity2;
    private static List<PortfolioEntity> oldPortfolio;
    private static List<AssetEntity> assetList1;
    private static List<AssetEntity> assetList2;
    private static Authentication auth;

    @BeforeClass
    public static void setUpData() {
        user = new UserEntity();
        user.setId(new Long(2));
        user.setEmail("luca@antilici.it");
        user.setPassword("pippo123");
        user.setDate(LocalDate.now());
        user.setLastUpdate(LocalDate.now().minusDays(1));

        assetClassEntity1 = new AssetClassEntity();
        assetClassEntity1.setId(new Long(1));
        assetClassEntity1.setName("bonds");

        assetEntity2 = new AssetEntity();
        assetEntity2.setId(new Long(2));
        assetEntity2.setAssetClass(assetClassEntity1);
        assetEntity2.setName("Ultra U.S. Treasury Bond Futures");
        assetEntity2.setDataSource("CHRIS/CME_UL1");
        assetEntity2.setPercentage(new BigDecimal(20.00));
        assetEntity2.setRemarksIndex(1);
        assetEntity2.setLastUpdate(LocalDate.now());
        assetEntity1 = new AssetEntity();
        assetEntity1.setId(new Long(2));
        assetEntity1.setAssetClass(assetClassEntity1);
        assetEntity1.setName("U.S. Treasury Bond Futures");
        assetEntity1.setDataSource("CHRIS/CME_US1");
        assetEntity1.setPercentage(new BigDecimal(80.00));
        assetEntity1.setRemarksIndex(1);
        assetEntity1.setLastUpdate(LocalDate.now());

        PortfolioEntity portfolioEntityToday1 = new PortfolioEntity();
        portfolioEntityToday1.setId(3L);
        portfolioEntityToday1.setUser(user);
        portfolioEntityToday1.setAsset(assetEntity1);
        portfolioEntityToday1.setAssetClass(assetClassEntity1);
        portfolioEntityToday1.setUnits(new BigDecimal(10.50));
        portfolioEntityToday1.setValue(new BigDecimal(241.5));
        portfolioEntityToday1.setDate(LocalDate.now());
        PortfolioEntity portfolioEntityToday2 = new PortfolioEntity();
        portfolioEntityToday2.setId(4L);
        portfolioEntityToday2.setUser(user);
        portfolioEntityToday2.setAsset(assetEntity2);
        portfolioEntityToday2.setAssetClass(assetClassEntity1);
        portfolioEntityToday2.setUnits(new BigDecimal(10.50));
        portfolioEntityToday2.setValue(new BigDecimal(210));
        portfolioEntityToday2.setDate(LocalDate.now());
        PortfolioEntity portfolioEntityYesterday1 = new PortfolioEntity();
        portfolioEntityYesterday1.setId(1L);
        portfolioEntityYesterday1.setUser(user);
        portfolioEntityYesterday1.setAsset(assetEntity1);
        portfolioEntityYesterday1.setAssetClass(assetClassEntity1);
        portfolioEntityYesterday1.setUnits(new BigDecimal(10));
        portfolioEntityYesterday1.setValue(new BigDecimal(220));
        portfolioEntityYesterday1.setDate(LocalDate.now().minusDays(1));
        PortfolioEntity portfolioEntityYesterday2 = new PortfolioEntity();
        portfolioEntityYesterday2.setId(1L);
        portfolioEntityYesterday2.setUser(user);
        portfolioEntityYesterday2.setAsset(assetEntity2);
        portfolioEntityYesterday2.setAssetClass(assetClassEntity1);
        portfolioEntityYesterday2.setUnits(new BigDecimal(11));
        portfolioEntityYesterday2.setValue(new BigDecimal(220));
        portfolioEntityYesterday2.setDate(LocalDate.now().minusDays(1));

        oldPortfolio = new ArrayList<>();
        oldPortfolio.add(portfolioEntityToday1);
        oldPortfolio.add(portfolioEntityToday2);
        oldPortfolio.add(portfolioEntityYesterday1);
        oldPortfolio.add(portfolioEntityYesterday2);

        assetList1 = new ArrayList<>();
        assetList1.add(assetEntity1);
        assetList2 = new ArrayList<>();
        assetList2.add(assetEntity2);

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
    public void getPortfolioForPeriodZeroPeriod() {
        PeriodDTO request = new PeriodDTO();
        request.setPeriod(0);
        List<Value> sumValues = new ArrayList<>();
        Value value1 = new Value(oldPortfolio.get(0).getDate(), oldPortfolio.get(0).getValue().add(oldPortfolio.get(1).getValue()));
        Value value2 = new Value(oldPortfolio.get(2).getDate(), oldPortfolio.get(2).getValue().add(oldPortfolio.get(3).getValue()));
        sumValues.add(value1);
        sumValues.add(value2);
        List<Value> sumAssetClassValues = new ArrayList<>();
        Value assetClassValue1 = new Value(oldPortfolio.get(0).getDate(), oldPortfolio.get(0).getValue().add(oldPortfolio.get(1).getValue()));
        Value assetClassValue2 = new Value(oldPortfolio.get(2).getDate(), oldPortfolio.get(2).getValue().add(oldPortfolio.get(3).getValue()));
        sumAssetClassValues.add(assetClassValue1);
        sumAssetClassValues.add(assetClassValue2);

        when(portfolioRep.findByUser(user)).thenReturn(oldPortfolio);
        when(portfolioRep.sumValues(user)).thenReturn(sumValues);
        when(portfolioRep.sumValuesForAssetClass(assetClassEntity1, user)).thenReturn(sumAssetClassValues);
        GenericResponse<List<PortfolioDTO>> ctrlResponse = (GenericResponse<List<PortfolioDTO>>) portfolioCl.getPortfolioForPeriod(request, auth);
        verify(portfolioRep).sumValues(user);
        assertEquals(1, ctrlResponse.getResponse());
        assertEquals(2, ctrlResponse.getData().size());
    }

    @Test
    public void getCurrentPortfolioSuccess() {
        List<PortfolioEntity> currentPortfolio = new ArrayList<>();
        currentPortfolio.add(oldPortfolio.get(0));
        currentPortfolio.add(oldPortfolio.get(1));

        PortfolioElementDTO portfolioElementDTO1 = new PortfolioElementDTO();
        portfolioElementDTO1.setId(oldPortfolio.get(0).getId());
        portfolioElementDTO1.setValue(new BigDecimal("451.5000"));

        List<PortfolioElementDTO> portfolioElementDTOList = new ArrayList<>();
        portfolioElementDTOList.add(portfolioElementDTO1);

        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setList(portfolioElementDTOList);
        portfolioDTO.setDate(oldPortfolio.get(0).getDate().toString());

        Value value = new Value(oldPortfolio.get(0).getDate(), new BigDecimal(451.5000));

        when(portfolioRep.findByUserAndDate(user, user.getLastUpdate())).thenReturn(currentPortfolio);
        when(portfolioRep.sumValuesForAssetClass(oldPortfolio.get(0).getAssetClass(), user, user.getLastUpdate())).thenReturn(value);
        when(portfolioRep.sumValues(user, user.getLastUpdate())).thenReturn(value);
        GenericResponse<PortfolioDTO> ctrlResponse = (GenericResponse<PortfolioDTO>) portfolioCl.getCurrentPortfolio(auth);
        verify(portfolioRep).findByUserAndDate(user, user.getLastUpdate());
        assertEquals(1, ctrlResponse.getResponse());
        assertEquals(portfolioDTO, ctrlResponse.getData());
    }

    @Test
    public void getCurrentPortfolioNullPortfolio() {
        when(portfolioRep.findByUserAndDate(user, user.getLastUpdate())).thenReturn(new ArrayList<PortfolioEntity>());
        GenericResponse<String> ctrlResponse = (GenericResponse<String>) portfolioCl.getCurrentPortfolio(auth);
        verify(portfolioRep).findByUserAndDate(user, user.getLastUpdate());
        assertEquals(0, ctrlResponse.getResponse());
        assertEquals(ControllerConstants.EMPTY_PORTFOLIO, ctrlResponse.getData());
    }
}
