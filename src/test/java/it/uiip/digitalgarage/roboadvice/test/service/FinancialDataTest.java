package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.converter.AssetClassConverter;
import it.uiip.digitalgarage.roboadvice.logic.operator.FinancialDataOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.FinancialDataController;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataElementDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class FinancialDataTest {
	
    @InjectMocks
    @Autowired
    private FinancialDataOperator financialDataOp;

    @Mock
    private AssetClassRepository assetClassRep;

    @Mock
    private FinancialDataRepository financialDataRep;

    @Mock
    private AssetRepository assetRep;

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getFinancialDataSetTest() {
//        AssetClassEntity assetClassEntity1 = new AssetClassEntity();
//        assetClassEntity1.setId(new Long(1));
//        assetClassEntity1.setName("bonds");
//        AssetClassEntity assetClassEntity2 = new AssetClassEntity();
//        assetClassEntity2.setId(new Long(3));
//        assetClassEntity2.setName("stocks");
//        List<AssetClassEntity> assetClasses = new ArrayList<>();
//        assetClasses.add(assetClassEntity1);
//        assetClasses.add(assetClassEntity2);
//        List<AssetClassEntity> assetClassEntityList = new ArrayList<>();
//        assetClassEntityList.add(assetClassEntity1);
//        assetClassEntityList.add(assetClassEntity2);
//
//        AssetEntity assetEntity1 = new AssetEntity();
//        assetEntity1.setId(new Long(2));
//        assetEntity1.setAssetClass(assetClassEntity1);
//        assetEntity1.setName("Ultra U.S. Treasury Bond Futures");
//        assetEntity1.setDataSource("CHRIS/CME_UL1");
//        assetEntity1.setPercentage(new BigDecimal(20.00));
//        assetEntity1.setRemarksIndex(1);
//        assetEntity1.setLastUpdate(LocalDate.now());
//        AssetEntity assetEntity2 = new AssetEntity();
//        assetEntity2.setId(new Long(8));
//        assetEntity2.setAssetClass(assetClassEntity2);
//        assetEntity2.setName("Microsoft");
//        assetEntity2.setDataSource("WIKI/MSFT");
//        assetEntity2.setPercentage(new BigDecimal(35.00));
//        assetEntity2.setRemarksIndex(11);
//        assetEntity2.setLastUpdate(LocalDate.now());
//        List<AssetEntity> assetListForAssetClass1 = new ArrayList<>();
//        assetListForAssetClass1.add(assetEntity1);
//        List<AssetEntity> assetListForAssetClass2 = new ArrayList<>();
//        assetListForAssetClass2.add(assetEntity2);
//
//        FinancialDataEntity financialDataAsset1 = new FinancialDataEntity();
//        financialDataAsset1.setId(new Long(1));
//        financialDataAsset1.setAsset(assetEntity1);
//        financialDataAsset1.setValue(new BigDecimal(150.09));
//        financialDataAsset1.setDate(LocalDate.now().minusDays(1));
//        FinancialDataEntity financialDataAsset2 = new FinancialDataEntity();
//        financialDataAsset2.setId(new Long(2));
//        financialDataAsset2.setAsset(assetEntity2);
//        financialDataAsset2.setValue(new BigDecimal(50.23));
//        financialDataAsset2.setDate(LocalDate.now().minusDays(1));
//        FinancialDataEntity financialDataAsset1Today = new FinancialDataEntity();
//        financialDataAsset1Today.setId(new Long(3));
//        financialDataAsset1Today.setAsset(assetEntity1);
//        financialDataAsset1Today.setValue(new BigDecimal(159.09));
//        financialDataAsset1Today.setDate(LocalDate.now());
//        FinancialDataEntity financialDataAsset2Today = new FinancialDataEntity();
//        financialDataAsset2Today.setId(new Long(4));
//        financialDataAsset2Today.setAsset(assetEntity2);
//        financialDataAsset2Today.setValue(new BigDecimal(45.23));
//        financialDataAsset2Today.setDate(LocalDate.now());
//        List<FinancialDataEntity> assetHistory1 = new ArrayList<>();
//        assetHistory1.add(financialDataAsset1Today);
//        assetHistory1.add(financialDataAsset1);
//        List<FinancialDataEntity> assetHistory2 = new ArrayList<>();
//        assetHistory2.add(financialDataAsset2Today);
//        assetHistory2.add(financialDataAsset2);
//
//        FinancialDataElementDTO financialDTOElementAssetClass1 = new FinancialDataElementDTO();
//        financialDTOElementAssetClass1.setDate(LocalDate.now().minusDays(1).toString());
//        financialDTOElementAssetClass1.setValue(financialDataAsset1.getValue());
//        FinancialDataElementDTO financialDTOElementAssetClass1Today = new FinancialDataElementDTO();
//        financialDTOElementAssetClass1Today.setDate(LocalDate.now().toString());
//        financialDTOElementAssetClass1Today.setValue(financialDataAsset1Today.getValue());
//        FinancialDataElementDTO financialDTOElementAssetClass2 = new FinancialDataElementDTO();
//        financialDTOElementAssetClass2.setDate(LocalDate.now().minusDays(1).toString());
//        financialDTOElementAssetClass2.setValue(financialDataAsset2.getValue());
//        FinancialDataElementDTO financialDTOElementAssetClass2Today = new FinancialDataElementDTO();
//        financialDTOElementAssetClass2Today.setDate(LocalDate.now().toString());
//        financialDTOElementAssetClass2Today.setValue(financialDataAsset2Today.getValue());
//        List<FinancialDataElementDTO> financialDTOElementsAssetClass1 = new ArrayList<>();
//        financialDTOElementsAssetClass1.add(financialDTOElementAssetClass1);
//        financialDTOElementsAssetClass1.add(financialDTOElementAssetClass1Today);
//        List<FinancialDataElementDTO> financialDTOElementsAssetClass2 = new ArrayList<>();
//        financialDTOElementsAssetClass2.add(financialDTOElementAssetClass2);
//        financialDTOElementsAssetClass2.add(financialDTOElementAssetClass2Today);
//
//        AssetClassDTO assetClassDTO1 = new AssetClassDTO();
//        assetClassDTO1.setId(assetClassEntity1.getId());
//        assetClassDTO1.setName(assetClassEntity1.getName());
//        AssetClassDTO assetClassDTO2 = new AssetClassDTO();
//        assetClassDTO2.setId(assetClassEntity2.getId());
//        assetClassDTO2.setName(assetClassEntity2.getName());
//
//        FinancialDataDTO financialDTOAssetClass1 = new FinancialDataDTO();
//        financialDTOAssetClass1.setAssetClass(assetClassDTO1);
//        financialDTOAssetClass1.setList(financialDTOElementsAssetClass1);
//        FinancialDataDTO financialDTOAssetClass2 = new FinancialDataDTO();
//        financialDTOAssetClass2.setAssetClass(assetClassDTO2);
//        financialDTOAssetClass2.setList(financialDTOElementsAssetClass2);
//
//        List<FinancialDataDTO> financialDataDTOList = new ArrayList<>();
//        financialDataDTOList.add(financialDTOAssetClass1);
//        financialDataDTOList.add(financialDTOAssetClass2);
//
//        when(assetRep.findByAssetClass(assetClassEntity1)).thenReturn(assetListForAssetClass1);
//        when(assetRep.findByAssetClass(assetClassEntity2)).thenReturn(assetListForAssetClass2);
//        when(assetClassRep.findAll()).thenReturn(assetClassEntityList);
//        when(financialDataRep.findByAssetAndDateGreaterThanOrderByDateDesc(assetEntity1, LocalDate.now().minusDays(1))).thenReturn(assetHistory1);
//        when(financialDataRep.findByAssetAndDateGreaterThanOrderByDateDesc(assetEntity2, LocalDate.now().minusDays(1))).thenReturn(assetHistory2);
//        //verify(financialDataRep).findByAssetAndDateGreaterThanOrderByDateDesc(assetEntity1, LocalDate.now().minusDays(1));
//        List<FinancialDataDTO> response = financialDataOp.getFinancialDataSet(2);
//        assertEquals(financialDataDTOList, response);
    }

}
