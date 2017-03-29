package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

/**
 * This class contains the Rest-APIs related to the Asset Class
 *
 * @author Cristian Laurini
 */
@CrossOrigin("*")
@RestController
public class AssetClassController extends AbstractController {

	/**
	 * This method allows to get all the Asset Classes.
	 * The related API is <b>/getAssetClassSet</b>
	 *
	 * @return 	GenericResponse with response 1 and a List of AssetClassDTOs.
	 */
	@RequestMapping("/getAssetClassSet")
	@ResponseBody
	public GenericResponse<?> getAssetClassSet() {
		Long start = System.currentTimeMillis();
		List<AssetClassDTO> result = this.assetClassOp.getAssetClassSet();
		Long end = System.currentTimeMillis();
		System.out.println("GetAssetClassSet in " + (end - start) + " ms");
		return new GenericResponse<List<AssetClassDTO>>(1, result);
	}
	
}
