package it.uiip.digitalgarage.roboadvice.test.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class PortfolioOperatorTest {

	@InjectMocks
	@Autowired
	private PortfolioOperator portfolioOp;

	@Mock
	private CapitalRepository capitalRep;

	@Mock
	private CustomStrategyRepository customStrategyRep;

	@Mock
	private PortfolioRepository portfolioRep;

	@Mock
	private AssetRepository assetRep;

	@Mock
	private FinancialDataRepository financialDataRep;

	private static UserEntity user;
	private static AssetClassEntity assetClassEntity1;
	private static AssetClassEntity assetClassEntity2;
	private static AssetEntity assetEntity1;
	private static AssetEntity assetEntity2;
	private static FinancialDataEntity financialDataEntity1;
	private static FinancialDataEntity financialDataEntity2;
	private static List<PortfolioEntity> oldPortfolio;
	private static List<CustomStrategyEntity> customStrategyEntityList;
	private static Map<Long, List<AssetEntity>> mapAssets;
	private static Map<Long, FinancialDataEntity> mapFD;
	private static List<AssetEntity> assetList1;
	private static List<AssetEntity> assetList2;

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
		financialDataEntity2.setAsset(assetEntity2);
		financialDataEntity2.setValue(new BigDecimal(50.23));
		financialDataEntity2.setDate(LocalDate.now().minusDays(1));

		PortfolioEntity portfolioEntity1 = new PortfolioEntity();
		portfolioEntity1.setId(new Long(1));
		portfolioEntity1.setUser(user);
		portfolioEntity1.setAsset(assetEntity1);
		portfolioEntity1.setAssetClass(assetClassEntity1);
		portfolioEntity1.setUnits(new BigDecimal(10.50));
		portfolioEntity1.setValue(new BigDecimal(1296.2954));
		portfolioEntity1.setDate(LocalDate.now().minusDays(1));
		PortfolioEntity portfolioEntity2 = new PortfolioEntity();
		portfolioEntity2.setId(new Long(2));
		portfolioEntity2.setUser(user);
		portfolioEntity2.setAsset(assetEntity2);
		portfolioEntity2.setAssetClass(assetClassEntity2);
		portfolioEntity2.setUnits(new BigDecimal(11.4567));
		portfolioEntity2.setValue(new BigDecimal(515.5515));
		portfolioEntity2.setDate(LocalDate.now().minusDays(1));
		oldPortfolio = new ArrayList<>();
		oldPortfolio.add(portfolioEntity1);
		oldPortfolio.add(portfolioEntity2);

		customStrategyEntityList = new ArrayList<>();
		CustomStrategyEntity customStrategyEntity1 = new CustomStrategyEntity();
		customStrategyEntity1.setId(new Long(1));
		customStrategyEntity1.setUser(user);
		customStrategyEntity1.setAssetClass(assetClassEntity1);
		customStrategyEntity1.setPercentage(new BigDecimal(55.50));
		customStrategyEntity1.setActive(true);
		customStrategyEntity1.setDate(LocalDate.now());
		CustomStrategyEntity customStrategyEntity2 = new CustomStrategyEntity();
		customStrategyEntity2.setId(new Long(2));
		customStrategyEntity2.setUser(user);
		customStrategyEntity2.setAssetClass(assetClassEntity2);
		customStrategyEntity2.setPercentage(new BigDecimal(44.50));
		customStrategyEntity2.setActive(true);
		customStrategyEntity2.setDate(LocalDate.now());
		customStrategyEntityList.add(customStrategyEntity1);
		customStrategyEntityList.add(customStrategyEntity2);

		assetList1 = new ArrayList<>();
		assetList1.add(assetEntity1);
		assetList2 = new ArrayList<>();
		assetList2.add(assetEntity2);

		mapAssets = new HashMap<>();
		mapAssets.put(assetClassEntity1.getId(), assetList1);
		mapAssets.put(assetClassEntity2.getId(), assetList2);

		mapFD = new HashMap<>();
		mapFD.put(assetEntity1.getId(), financialDataEntity1);
		mapFD.put(assetEntity2.getId(), financialDataEntity2);
	}

	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void createUserPortfolioTestCapitalNotEmpty() {

		CapitalEntity capitalEntity = new CapitalEntity();
		capitalEntity.setId(new Long(12));
		capitalEntity.setUser(user);
		capitalEntity.setAmount(new BigDecimal(1236.34));
		capitalEntity.setDate(LocalDate.now().minusDays(1));

		BigDecimal amountPerAsset = new BigDecimal("137.23374000");
		BigDecimal units = new BigDecimal("0.91434299");
		PortfolioEntity portfolioEntity = new PortfolioEntity();
		portfolioEntity.setId(null);
		portfolioEntity.setUser(user);
		portfolioEntity.setAsset(assetEntity1);
		portfolioEntity.setAssetClass(assetClassEntity1);
		portfolioEntity.setDate(LocalDate.now());
		portfolioEntity.setValue(amountPerAsset);
		portfolioEntity.setUnits(units);

		when(capitalRep.findByUserAndDate(user, user.getLastUpdate())).thenReturn(capitalEntity);
		when(customStrategyRep.findByUserAndActive(user, true)).thenReturn(customStrategyEntityList);
		when(assetRep.findByAssetClass(assetClassEntity1)).thenReturn(assetList1);
		when(assetRep.findByAssetClass(assetClassEntity2)).thenReturn(assetList2);
		when(financialDataRep.findByAssetAndDate(assetEntity1,assetEntity1.getLastUpdate())).thenReturn(financialDataEntity1);
		when(financialDataRep.findByAssetAndDate(assetEntity2,assetEntity2.getLastUpdate())).thenReturn(financialDataEntity2);
		when(portfolioRep.findByUserAndAssetAndDate(user, assetEntity1, LocalDate.now())).thenReturn(portfolioEntity);
		User u = new User();
		u.setUser(user);
		u.setStrategy(customStrategyEntityList);
		u.setCapital(capitalEntity);
		boolean response = portfolioOp.createUserPortfolio(u, mapAssets, mapFD);
		verify(portfolioRep).save(portfolioEntity);
		assertTrue(response);
	}

	@Test
	public void createUserPortfolioFailNullCapital() {
		User u = new User();
		u.setUser(user);
		u.setStrategy(customStrategyEntityList);
		u.setCapital(null);
		boolean response = portfolioOp.createUserPortfolio(u, mapAssets, mapFD);
		assertFalse(response);
	}

	@Test
	public void createUserPortfolioFailNullCustomStrategy() {
		CapitalEntity capitalEntity = new CapitalEntity();
		capitalEntity.setId(new Long(12));
		capitalEntity.setUser(user);
		capitalEntity.setAmount(new BigDecimal(1236.34));
		capitalEntity.setDate(LocalDate.now().minusDays(1));
		List<CustomStrategyEntity> customStrategyEntityList = new ArrayList<>();

		User u = new User();
		u.setUser(user);
		u.setCapital(capitalEntity);
		u.setStrategy(customStrategyEntityList);
		when(capitalRep.findByUserAndDate(user, user.getLastUpdate())).thenReturn(capitalEntity);
		when(customStrategyRep.findByUserAndActive(user, true)).thenReturn(customStrategyEntityList);
		boolean response = portfolioOp.createUserPortfolio(u, mapAssets, mapFD );
		assertFalse(response);
	}

	@Test
	public void computeUserPortfolioSucces() {
		PortfolioEntity savedPortfolio = new PortfolioEntity();
		savedPortfolio.setId(null);
		savedPortfolio.setUser(user);
		savedPortfolio.setAsset(assetEntity1);
		savedPortfolio.setAssetClass(assetClassEntity1);
		savedPortfolio.setUnits(new BigDecimal(10.50));
		savedPortfolio.setValue(new BigDecimal("1575.9450000000000358113538823090493679046630859375"));
		savedPortfolio.setDate(LocalDate.now());
		PortfolioEntity savedPortfolio2 = new PortfolioEntity();
		savedPortfolio2.setId(null);
		savedPortfolio2.setUser(user);
		savedPortfolio2.setAsset(assetEntity2);
		savedPortfolio2.setAssetClass(assetClassEntity2);
		savedPortfolio2.setUnits(new BigDecimal(11.4566999999999996617816577781923115253448486328125));
		savedPortfolio2.setValue(new BigDecimal("575.470040999999947193202842754545747653129535559791412767556562091186833640676923096179962158203125"));
		savedPortfolio2.setDate(LocalDate.now());

		User u = new User();
		u.setUser(user);
		u.setPortfolio(oldPortfolio);

		when(portfolioRep.findByUserAndDate(user, user.getLastUpdate())).thenReturn(oldPortfolio);
		when(financialDataRep.findByAssetAndDate(assetEntity1,assetEntity1.getLastUpdate())).thenReturn(financialDataEntity1);
		when(financialDataRep.findByAssetAndDate(assetEntity2,assetEntity2.getLastUpdate())).thenReturn(financialDataEntity2);
		List<PortfolioEntity> response = portfolioOp.computeUserPortfolio(u, mapFD);
		verify(portfolioRep).save(savedPortfolio);
		verify(portfolioRep).save(savedPortfolio2);
		assertEquals(savedPortfolio,response.get(0));
		assertEquals(savedPortfolio2,response.get(1));
	}

	@Test
	public void computeUserPortfolioEmptyFinancialData() {
		User u = new User();
		u.setUser(user);
		u.setPortfolio(oldPortfolio);
		when(portfolioRep.findByUserAndDate(user, user.getLastUpdate())).thenReturn(oldPortfolio);
		when(financialDataRep.findByAssetAndDate(assetEntity1,assetEntity1.getLastUpdate())).thenReturn(financialDataEntity1);
		when(financialDataRep.findByAssetAndDate(assetEntity2,assetEntity2.getLastUpdate())).thenReturn(financialDataEntity2);
		List<PortfolioEntity> response = portfolioOp.computeUserPortfolio(u, new HashMap<Long, FinancialDataEntity>());
		assertNull(response);
	}
	
}
