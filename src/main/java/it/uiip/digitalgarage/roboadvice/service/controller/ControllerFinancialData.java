package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.quandl.QuandlOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerFinancialData extends GenericController {
	
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet() {
		List<FinancialDataEntity> result = this.financialDataRep.findAll();
		return new GenericResponse<List<FinancialDataEntity>>(1, result);
	}
	
	@RequestMapping("/updateFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> updateFinancialDataSet() {
		new QuandlOperator(financialDataRep, assetRep).updateFinancialDataSet();;
		return new GenericResponse<String>(1, "Done");
	}
	
	@RequestMapping("/initializeFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> initializeFinancialDataSet() {
		new QuandlOperator(financialDataRep, assetRep).initializeFinancialDataSet();
		return new GenericResponse<String>(1, "Done");
	}

}
