package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.controller.FinancialDataController;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class FinancialDataTest {

	/* TODO
	 * Test: getFinancialDataSet
	 * Test: getFinancialDataForAsset
	 * Test: getFinancialDataForAssetClass
	 * Test: findLastFinancialDataForAsset
	 * Test: updateFinancialDataSet
	 * Test: initializeFinancialDataSet
	*/
	
    @Autowired
    private FinancialDataController financialDataCtrl;

    @Test
    public void getFinancialDataSetTest() {
//        GenericResponse<?> response = this.financialDataCtrl.getFinancialDataSet();
//        List<?> list = (List<?>) response.getData();
//        assertEquals(1, response.getResponse());
//        assertFalse(list.isEmpty());
    }

    @Test
    public void getFinancialDataForAssetAllSet() {
//        DataRequestDTO dto = new DataRequestDTO();
//        dto.setId(new Long(1));
//        dto.setPeriod(0);
//        GenericResponse<?> response = this.financialDataCtrl.getFinancialDataForAsset(dto);
//        List<?> list = (List<?>) response.getData();
//        assertEquals(1, response.getResponse());
//        assertFalse(list.isEmpty());
    }
    
}
