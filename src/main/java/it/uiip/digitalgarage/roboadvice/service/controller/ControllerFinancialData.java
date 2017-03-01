package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerFinancialData {
	
	@Autowired
	private FinancialDataRepository daoFinancialData;
	
	@RequestMapping("/getFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> getFinancialDataSet() {
		List<FinancialDataEntity> result = (List<FinancialDataEntity>) this.daoFinancialData.findAll();
		return new GenericResponse<List<FinancialDataEntity>>(1, result);
	}

}
