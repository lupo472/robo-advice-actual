package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import javax.validation.Valid;

import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

/**
 * This class contains the Rest-APIs related to the FinancialData.
 *
 * @author Cristian Laurini
 */
@CrossOrigin("*")
@RestController
public class FinancialDataController extends AbstractController {

	/**
	 * This method allow to get all the Financial Data.
	 * The related API is <b>/getFinancialDataSet</b>
	 *
	 * @return 	GenericResponse with response 0 and a message if some problem occurs, or with response 1 and
	 * 			a List of FinancialDataDTOs instead.
	 */
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet(@Valid @RequestBody PeriodDTO period) {
		List<FinancialDataDTO> result = this.financialDataOp.getFinancialDataSet(period.getPeriod());
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<List<FinancialDataDTO>>(1, result);
	}

}
