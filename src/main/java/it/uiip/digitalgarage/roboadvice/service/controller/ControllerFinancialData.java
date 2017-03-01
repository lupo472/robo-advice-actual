package it.uiip.digitalgarage.roboadvice.service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.logic.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.logic.quandl.QuandlOperator;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBInitializer;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlUpdateScheduler;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerFinancialData {
	
	@Autowired
	private FinancialDataRepository daoFinancialData;
	
	@Autowired
	private AssetRepository daoAsset;
	
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet() {
		List<FinancialDataEntity> result = (List<FinancialDataEntity>) this.daoFinancialData.findAll();
		return new GenericResponse<List<FinancialDataEntity>>(1, result);
	}
	
	@RequestMapping("/updateFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> updateFinancialDataSet() {
		new QuandlOperator(daoFinancialData, daoAsset).updateFinancialDataSet();;
		return null;
	}
	
	
	@RequestMapping("/initializeFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> initializeFinancialDataSet() {
		new QuandlOperator(daoFinancialData, daoAsset).initializeFinancialDataSet();
		return null;
	}
	
	@RequestMapping("/prova")
	@ResponseBody
	public GenericResponse<?> prova() {
		AssetEntity asset = this.daoAsset.findById(new Long(1));
		//return new GenericResponse<LocalDate>(1, LocalDate.of(2017, 01, 01));
		List<FinancialDataEntity> data = this.daoFinancialData.findByAssetIdAndDate(asset.getId(), LocalDate.of(2017, 02, 01));
		return new GenericResponse<List<FinancialDataEntity>>(1, data);
	}

}
