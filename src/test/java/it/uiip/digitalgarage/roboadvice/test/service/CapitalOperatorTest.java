package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.CapitalOperator;
import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CapitalOperatorTest {
    @InjectMocks
    @Autowired
    private CapitalOperator capitalOp;

    @Spy
    private PortfolioOperator portfolioOp;

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
    public void computeCapitalTestSuccess() {
        PortfolioEntity portfolioEntity1 = new PortfolioEntity();
        portfolioEntity1.setId(1L);
        portfolioEntity1.setUser(user);
        portfolioEntity1.setAsset(assetEntity1);
        portfolioEntity1.setAssetClass(assetClassEntity1);
        portfolioEntity1.setUnits(new BigDecimal(10.50));
        portfolioEntity1.setValue(new BigDecimal(1296.2954));
        portfolioEntity1.setDate(LocalDate.now());
        PortfolioEntity portfolioEntity2 = new PortfolioEntity();
        portfolioEntity2.setId(2L);
        portfolioEntity2.setUser(user);
        portfolioEntity2.setAsset(assetEntity2);
        portfolioEntity2.setAssetClass(assetClassEntity2);
        portfolioEntity2.setUnits(new BigDecimal(11.4567));
        portfolioEntity2.setValue(new BigDecimal(515.5515));
        portfolioEntity2.setDate(LocalDate.now());
        List<PortfolioEntity> portfolio = new ArrayList<>();
        portfolio.add(portfolioEntity1);
        portfolio.add(portfolioEntity2);

        CapitalEntity savedCapital = new CapitalEntity();
        savedCapital.setId(null);
        savedCapital.setUser(user);
        savedCapital.setDate(LocalDate.now());
        savedCapital.setAmount(new BigDecimal(1575.945));

        when(capitalRep.save(savedCapital)).thenReturn(savedCapital);
        when(portfolioRep.findByUserAndDate(user, user.getLastUpdate())).thenReturn(portfolio);
        when(financialDataRep.findByAssetAndDate(assetEntity1,assetEntity1.getLastUpdate())).thenReturn(financialDataEntity1);
        when(financialDataRep.findByAssetAndDate(assetEntity2,assetEntity2.getLastUpdate())).thenReturn(financialDataEntity2);
        doReturn(new BigDecimal(1575.945)).when(portfolioOp).evaluatePortfolio(mapFD, portfolio);
        CapitalEntity response = capitalOp.computeCapital(user, mapFD, portfolio);
        verify(capitalRep).save(savedCapital);
        assertEquals(savedCapital, response);
    }

    @Test
    public void computeCapitalTestNullPortfolio() {
        doReturn(null).when(portfolioOp).evaluatePortfolio(mapFD, null);
        CapitalEntity response = capitalOp.computeCapital(user, mapFD, null);
        assertNull(response);
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

        boolean response = capitalOp.addCapital(capitalRequestDTO, user);
        verify(capitalRep).save(updatedCapital);
        assertTrue(response);
    }


    @Test
    public void getCapitalPeriodOneDayBackwardTodaySunday() {
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setPeriod(2);

        LocalDate sunday = LocalDate.parse("2017-03-26");
        LocalDate sathurday = LocalDate.parse("2017-03-25");
        CapitalEntity sundayCapital = new CapitalEntity();
        sundayCapital.setId(1L);
        sundayCapital.setUser(user);
        sundayCapital.setAmount(new BigDecimal(10456.5643));
        sundayCapital.setDate(sunday);
        CapitalEntity sathurdayCapital = new CapitalEntity();
        sathurdayCapital.setId(2L);
        sathurdayCapital.setUser(user);
        sathurdayCapital.setAmount(new BigDecimal(10456.5643));
        sathurdayCapital.setDate(sathurday);

        List<CapitalEntity> capitals = new ArrayList<>();
        capitals.add(sundayCapital);
        capitals.add(sathurdayCapital);

        //TODO search another type of faking the date
        when(capitalRep.findByUserAndDateBetween(user,LocalDate.now().minusDays(1), LocalDate.now())).thenReturn(capitals);
        List<CapitalDTO> opResponse = capitalOp.getCapitalPeriod(periodDTO, auth);
        assertEquals(2, opResponse.size());

    }


    @Test
    public void getCapitalPeriodZeroPeriodTodaySunday() {
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setPeriod(0);

        LocalDate sunday = LocalDate.parse("2017-03-26");
        LocalDate sathurday = LocalDate.parse("2017-03-25");
        LocalDate friday = LocalDate.parse("2017-03-24");

        CapitalEntity sundayCapital = new CapitalEntity();
        sundayCapital.setId(1L);
        sundayCapital.setUser(user);
        sundayCapital.setAmount(new BigDecimal(10456.5643));
        sundayCapital.setDate(sunday);
        CapitalEntity sathurdayCapital = new CapitalEntity();
        sathurdayCapital.setId(2L);
        sathurdayCapital.setUser(user);
        sathurdayCapital.setAmount(new BigDecimal(10456.5643));
        sathurdayCapital.setDate(sathurday);
        CapitalEntity fridayCapital = new CapitalEntity();
        fridayCapital.setId(3L);
        fridayCapital.setUser(user);
        fridayCapital.setAmount(new BigDecimal(10056.12));
        fridayCapital.setDate(friday);

        List<CapitalEntity> capitals = new ArrayList<>();
        capitals.add(sundayCapital);
        capitals.add(sathurdayCapital);
        capitals.add(fridayCapital);

        when(capitalRep.findByUser(user)).thenReturn(capitals);
        List<CapitalDTO> opResponse = capitalOp.getCapitalPeriod(periodDTO, auth);
        assertEquals(3, opResponse.size());

    }
}