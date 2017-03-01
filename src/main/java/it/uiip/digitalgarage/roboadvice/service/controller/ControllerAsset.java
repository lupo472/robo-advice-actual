package it.uiip.digitalgarage.roboadvice.service.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerAsset {

	@RequestMapping("/getAssetSet")
	@ResponseBody
	public GenericResponse<?> getAssetSet() {
		//List<AssetClassEntity> result = (List<AssetClassEntity>) this.daoAssetClass.findAll();
		return new GenericResponse<String>(1, "Ciao"); //new GenericResponse<List<AssetClassEntity>>(1, result);
	}
	
}
