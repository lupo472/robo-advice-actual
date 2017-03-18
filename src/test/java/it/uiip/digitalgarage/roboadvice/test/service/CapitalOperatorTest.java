package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.CapitalOperator;
import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.CapitalController;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class CapitalOperatorTest {

	/* TODO
	 * Test: addCapital
	 * Test: getCurrentCapital
	 * Test: computeCapital
	 * Test: getCapitalPeriod
	*/

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

    private UserEntity user;
    private FinancialDataEntity financialDataEntity1;
    private FinancialDataEntity financialDataEntity2;
    private AssetClassEntity assetClassEntity1;
    private AssetClassEntity assetClassEntity2;
    private AssetEntity assetEntity1;
    private AssetEntity assetEntity2;

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
        user = new UserEntity();
        user.setId(new Long(2));
        user.setEmail("luca@antilici.it");
        user.setPassword("pippo123");
        user.setDate(LocalDate.now());
        user.setLastUpdate(LocalDate.now().minusDays(1));

        assetClassEntity1 = new AssetClassEntity();
        assetClassEntity1.setId(new Long(1));
        assetClassEntity1.setName("bonds");
        assetClassEntity2 = new AssetClassEntity();
        assetClassEntity2.setId(new Long(3));
        assetClassEntity2.setName("stocks");
        assetEntity1 = new AssetEntity();
        assetEntity1.setId(new Long(2));
        assetEntity1.setAssetClass(assetClassEntity1);
        assetEntity1.setName("Ultra U.S. Treasury Bond Futures");
        assetEntity1.setDataSource("CHRIS/CME_UL1");
        assetEntity1.setPercentage(new BigDecimal(20.00));
        assetEntity1.setRemarksIndex(1);
        assetEntity1.setLastUpdate(LocalDate.now().minusDays(1));
        assetEntity2 = new AssetEntity();
        assetEntity2.setId(new Long(8));
        assetEntity2.setAssetClass(assetClassEntity2);
        assetEntity2.setName("Microsoft");
        assetEntity2.setDataSource("WIKI/MSFT");
        assetEntity2.setPercentage(new BigDecimal(35.00));
        assetEntity2.setRemarksIndex(11);
        assetEntity2.setLastUpdate(LocalDate.now().minusDays(1));

        financialDataEntity1 = new FinancialDataEntity();
        financialDataEntity1.setId(new Long(1));
        financialDataEntity1.setAsset(assetEntity1);
        financialDataEntity1.setValue(new BigDecimal(150.09));
        financialDataEntity1.setDate(LocalDate.now().minusDays(1));
        financialDataEntity2 = new FinancialDataEntity();
        financialDataEntity2.setId(new Long(1));
        financialDataEntity2.setAsset(assetEntity1);
        financialDataEntity2.setValue(new BigDecimal(50.23));
        financialDataEntity2.setDate(LocalDate.now().minusDays(1));
    }

    @Test
    public void computeCapitalSuccess() {
        PortfolioEntity portfolioEntity1 = new PortfolioEntity();
        portfolioEntity1.setId(new Long(1));
        portfolioEntity1.setUser(user);
        portfolioEntity1.setAsset(assetEntity1);
        portfolioEntity1.setAssetClass(assetClassEntity1);
        portfolioEntity1.setUnits(new BigDecimal(10.50));
        portfolioEntity1.setValue(new BigDecimal(1296.2954));
        portfolioEntity1.setDate(LocalDate.now());
        PortfolioEntity portfolioEntity2 = new PortfolioEntity();
        portfolioEntity2.setId(new Long(2));
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
        doReturn(new BigDecimal(1575.945)).when(portfolioOp).evaluatePortfolio(user);
        boolean response = capitalOp.computeCapital(user);
        verify(capitalRep).save(savedCapital);
        assertTrue(response);

    }

    @Test
    public void computeCapitalNullPortfolio() {
        doReturn(null).when(portfolioOp).evaluatePortfolio(user);
        boolean response = capitalOp.computeCapital(user);
        assertFalse(response);
    }


//    @Test
//    public void getCurrentCapitalValidUser() {
//    	UserRegisteredDTO user = new UserRegisteredDTO();
//        user.setId(new Long(1));
//        user.setEmail("test@test.it");
//        user.setPassword("testit");
//        GenericResponse<?> response = this.capitalCtrl.getCurrentCapital(user);
//        CapitalResponseDTO capital = (CapitalResponseDTO) response.getData();
//        assertEquals(user.getId(), capital.getIdUser());
//        assertEquals(1, response.getResponse());
//        assertFalse(capital.getAmount() == null);
//    }
//
//    @Test
//    public void getCurrentCapitalInvalidUser() {
//    	UserRegisteredDTO user = new UserRegisteredDTO();
//        user.setId(new Long(0));
//        user.setEmail("test@case.it");
//        user.setPassword("12345");
//        GenericResponse<?> response = this.capitalCtrl.getCurrentCapital(user);
//        assertEquals(0, response.getResponse());
//        assertEquals(ControllerConstants.ANY_CAPITAL, response.getData());
//    }
//
//    @Test
//    public void computeCapitalInvalidUser() {
//        UserRegisteredDTO user = new UserRegisteredDTO();
//        user.setId(new Long(0));
//        user.setEmail("test@case.it");
//        user.setPassword("12345");
//        GenericResponse<?> response = this.capitalCtrl.computeCapital(user);
//        assertTrue(response.getResponse() == 0);
//    }
//
//    @Test
//    public void getCapitalPeriodValidUserAllDays() {
//        DataRequestDTO dto = new DataRequestDTO();
//        dto.setId(new Long(1));
//        dto.setPeriod(0);
//        GenericResponse<?> response = this.capitalCtrl.getCapitalPeriod(dto);
//        List<?> list = (List<?>) response.getData();
//        CapitalResponseDTO capital = (CapitalResponseDTO) list.get(0);
//        assertEquals(dto.getId(), capital.getIdUser());
//        assertFalse(list.isEmpty());
//        assertTrue(list.size() > 1);
//    }
//
//    @Test
//    public void getCapitalPeriodValidUserMoreDays() {
//        DataRequestDTO dto = new DataRequestDTO();
//        dto.setId(new Long(1));
//        dto.setPeriod(30);
//        GenericResponse<?> response = this.capitalCtrl.getCapitalPeriod(dto);
//        List<?> list = (List<?>) response.getData();
//        CapitalResponseDTO capital = (CapitalResponseDTO) list.get(0);
//        assertFalse(list.isEmpty());
//        assertTrue(list.size() > 1);
//        assertEquals(dto.getId(), capital.getIdUser());
//    }

}