package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerAssetClass {

	@Autowired
	private AssetClassRepository daoAssetClass;
	
	@RequestMapping("/getAssetClassSet")
	@ResponseBody
	public GenericResponse<?> getAssetClassSet() {
		List<AssetClassEntity> result = (List<AssetClassEntity>) this.daoAssetClass.findAll();
		return new GenericResponse<List<AssetClassEntity>>(1, result);
	}
	
}
