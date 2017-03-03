package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.FinancialDataOperator;
import it.uiip.digitalgarage.roboadvice.logic.operator.QuandlOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DataForAssetRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class FinancialDataController extends AbstractController { 
	
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet() {
		this.financialDataOp = new FinancialDataOperator(this.financialDataRep);
		List<FinancialDataDTO> result = this.financialDataOp.getFinancialDataSet();
		return new GenericResponse<List<FinancialDataDTO>>(1, result);
	}
	
	@RequestMapping("/getFinancialDataForAsset")
	@ResponseBody
	public GenericResponse<?> getFinancialDataForAsset(@Valid @RequestBody DataForAssetRequestDTO request) {
		this.financialDataOp = new FinancialDataOperator(this.financialDataRep);
		List<FinancialDataDTO> result = this.financialDataOp.getFinancialDataSet(request);
		return new GenericResponse<List<FinancialDataDTO>>(1, result);
	}
	
	@RequestMapping("/findLastFinancialDataForAsset")
	@ResponseBody
	public GenericResponse<?> findLastFinancialDataForAsset(@Valid @RequestBody AssetDTO asset) {
		this.financialDataOp = new FinancialDataOperator(this.financialDataRep);
		FinancialDataDTO result = null;//this.financialDataOp.findLast(AssetDTO asset);
		return new GenericResponse<FinancialDataDTO>(1, result);
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
