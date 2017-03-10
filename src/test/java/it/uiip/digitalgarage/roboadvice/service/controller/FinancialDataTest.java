package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Luca on 10/03/2017.
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class FinancialDataTest {

    @Autowired
    private FinancialDataController financialDataCtrl;

    @Test
    public void getFinancialDataSetTest() throws Exception {
        GenericResponse<List<FinancialDataDTO>> response = (GenericResponse<List<FinancialDataDTO>>) this.financialDataCtrl.getFinancialDataSet();
        assertEquals(1, response.getResponse());
        assertFalse(response.getData().isEmpty());
    }

    @Test
    public void getFinancialDataForAssetAllSet() throws Exception {
        DataRequestDTO dto = new DataRequestDTO();
        dto.setId(new Long(1));
        dto.setPeriod(0);
        GenericResponse<List<FinancialDataDTO>> response = (GenericResponse<List<FinancialDataDTO>>) this.financialDataCtrl.getFinancialDataForAsset(dto);
        assertEquals(1, response.getResponse());
        assertFalse(response.getData().isEmpty());
    }
}
