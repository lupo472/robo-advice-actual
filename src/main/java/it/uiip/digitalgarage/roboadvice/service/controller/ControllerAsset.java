package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerAsset extends GenericController {

	@RequestMapping("/getAssetSet")
	@ResponseBody
	public GenericResponse<?> getAssetSet() {
		List<AssetEntity> result = this.daoAsset.findAll();
		return new GenericResponse<List<AssetEntity>>(1, result);
	}
	
}
