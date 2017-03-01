package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerAsset {
	
	@Autowired
	private AssetRepository daoAsset;

	@RequestMapping("/getAssetSet")
	@ResponseBody
	public GenericResponse<?> getAssetSet() {
		List<AssetEntity> result = (List<AssetEntity>) this.daoAsset.findAll();
		return new GenericResponse<List<AssetEntity>>(1, result);
	}
	
}
