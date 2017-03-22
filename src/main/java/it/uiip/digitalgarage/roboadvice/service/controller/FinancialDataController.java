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
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class FinancialDataController extends AbstractController { 
	
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet(@Valid @RequestBody PeriodRequestDTO period) {
		List<FinancialDataDTO> result = this.financialDataOp.getFinancialDataSet(period.getPeriod());
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<List<FinancialDataDTO>>(1, result);
	}
		
/************************************************************************************************
 * 										Test Method												*
 * ******************************************************************************************** *	
 *  @RequestMapping("/updateFinancialDataSet")													*
 *	@ResponseBody																				*
 *	public GenericResponse<?> updateFinancialDataSet() {										*
 *		this.quandlOp.updateFinancialDataSet();													*
 *		return new GenericResponse<String>(1, ControllerConstants.DONE);						*
 *	}																							*
 ************************************************************************************************/
	
/************************************************************************************************
 * 										Test Method												*
 * ******************************************************************************************** *	
 *  @RequestMapping("/initializeFinancialDataSet")												*
 *	@ResponseBody																				*
 *	public GenericResponse<?> initializeFinancialDataSet() {									*
 *		this.quandlOp.initializeFinancialDataSet();												*
 *		return new GenericResponse<String>(1, ControllerConstants.DONE);						*
 *	}																							*
 ************************************************************************************************/

}
