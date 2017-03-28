package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.AdviceOperator;
import it.uiip.digitalgarage.roboadvice.logic.operator.DefaultStrategyOperator;
import it.uiip.digitalgarage.roboadvice.logic.operator.ForecastingOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;
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
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 28/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class AdviceOperatorTest {

    @InjectMocks
    @Autowired
    private AdviceOperator adviceOp;

    @Spy
    private ForecastingOperator forecastingOp;

    @Spy
    private DefaultStrategyOperator defaultStrategyOp;

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
    public void getAdviceCurrentStrategyBestTest() {
        PeriodDTO period = new PeriodDTO();
        period.setPeriod(2);

        PortfolioElementDTO portfolioElementBonds1 = new PortfolioElementDTO();
        portfolioElementBonds1.setName("bonds");
        portfolioElementBonds1.setValue(new BigDecimal(1234));
        portfolioElementBonds1.setPercentage(new BigDecimal(95));
        PortfolioElementDTO portfolioElementCommodities1 = new PortfolioElementDTO();
        portfolioElementCommodities1.setName("commodities");
        portfolioElementCommodities1.setValue(new BigDecimal(124));
        portfolioElementCommodities1.setPercentage(new BigDecimal(5));
        List<PortfolioElementDTO> portfolioElementListToday = new ArrayList<>();
        portfolioElementListToday.add(portfolioElementBonds1);
        portfolioElementListToday.add(portfolioElementCommodities1);
        PortfolioDTO portfolioDTOToday = new PortfolioDTO();
        portfolioDTOToday.setList(portfolioElementListToday);
        portfolioDTOToday.setDate(LocalDate.now().toString());

        PortfolioElementDTO portfolioElementBonds2 = new PortfolioElementDTO();
        portfolioElementBonds2.setName("bonds");
        portfolioElementBonds2.setValue(new BigDecimal(1210));
        portfolioElementBonds2.setPercentage(new BigDecimal(95));
        PortfolioElementDTO portfolioElementCommodities2 = new PortfolioElementDTO();
        portfolioElementCommodities2.setName("commodities");
        portfolioElementCommodities2.setValue(new BigDecimal(178));
        portfolioElementCommodities2.setPercentage(new BigDecimal(5));
        List<PortfolioElementDTO> portfolioElementListTomorrow = new ArrayList<>();
        portfolioElementListTomorrow.add(portfolioElementBonds1);
        portfolioElementListTomorrow.add(portfolioElementCommodities1);
        PortfolioDTO portfolioDTOTomorrow = new PortfolioDTO();
        portfolioDTOTomorrow.setList(portfolioElementListTomorrow);
        portfolioDTOTomorrow.setDate(LocalDate.now().plusDays(1).toString());


        List<PortfolioDTO> currentStrategyPortfolio = new ArrayList<>();
        currentStrategyPortfolio.add(portfolioDTOToday);
        currentStrategyPortfolio.add(portfolioDTOTomorrow);
        doReturn(currentStrategyPortfolio).when(forecastingOp).getDemo(period, auth);
    }

}
