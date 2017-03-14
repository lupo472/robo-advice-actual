package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/roboadvice")
public class AssetController extends AbstractController {

	@RequestMapping("/getAssetSet")
	@ResponseBody
	public GenericResponse<?> getAssetSet() {
		List<AssetDTO> result = this.assetOp.getAssetSet();
		return new GenericResponse<List<AssetDTO>>(1, result);
	}
	
}
