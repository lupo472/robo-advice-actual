package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.QuandlOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerFinancialData extends GenericController {
	
	private QuandlOperator quandlOperator;
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet() {
		this.quandlOperator = new QuandlOperator(this.financialDataRep, this.assetRep);
		List<FinancialDataEntity> result = this.quandlOperator.getFinancialDataSet();
		return new GenericResponse<List<FinancialDataEntity>>(1, result);
	}
	
	@RequestMapping("/updateFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> updateFinancialDataSet() {
		this.quandlOperator = new QuandlOperator(this.financialDataRep, this.assetRep);
		this.quandlOperator.updateFinancialDataSet();;
		return new GenericResponse<String>(1, "Done");
	}
	
	@RequestMapping("/initializeFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> initializeFinancialDataSet() {
		this.quandlOperator = new QuandlOperator(this.financialDataRep, this.assetRep);
		this.quandlOperator.initializeFinancialDataSet();
		return new GenericResponse<String>(1, "Done");
	}

}
