package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.QuandlOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class FinancialDataController extends AbstractController { 
	
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet() {
		this.quandlOp = new QuandlOperator(this.financialDataRep, this.assetRep);
		List<FinancialDataEntity> result = this.quandlOp.getFinancialDataSet();
		return new GenericResponse<List<FinancialDataEntity>>(1, result);
	}
	
	@RequestMapping("/updateFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> updateFinancialDataSet() {
		this.quandlOp = new QuandlOperator(this.financialDataRep, this.assetRep);
		this.quandlOp.updateFinancialDataSet();;
		return new GenericResponse<String>(1, "Done");
	}
	
	@RequestMapping("/initializeFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> initializeFinancialDataSet() {
		this.quandlOp = new QuandlOperator(this.financialDataRep, this.assetRep);
		this.quandlOp.initializeFinancialDataSet();
		return new GenericResponse<String>(1, "Done");
	}

}
