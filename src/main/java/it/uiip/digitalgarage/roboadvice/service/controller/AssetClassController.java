package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.AssetClassOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class AssetClassController extends AbstractController {
	
	@RequestMapping("/getAssetClassSet")
	@ResponseBody
	public GenericResponse<?> getAssetClassSet() {
		this.assetClassOp = new AssetClassOperator(assetClassRep);
		List<AssetClassEntity> result = this.assetClassOp.getAssetClassSet();
		return new GenericResponse<List<AssetClassEntity>>(1, result);
	}
	
}
