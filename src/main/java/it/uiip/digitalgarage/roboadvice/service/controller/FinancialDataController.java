package it.uiip.digitalgarage.roboadvice.service.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class FinancialDataController extends AbstractController { 
	
//	@RequestMapping("/getFinancialDataSet")
//	@ResponseBody
//	public GenericResponse<?> getFinancialDataSet() {
//		List<FinancialDataDTO> result = this.financialDataOp.getFinancialDataSet();
//		return new GenericResponse<List<FinancialDataDTO>>(1, result);
//	}
	
//	@RequestMapping("/getFinancialDataForAssetClass")
//	@ResponseBody
//	public GenericResponse<?> getFinancialDataForAssetClass(@Valid @RequestBody DataRequestDTO request) {
//		List<FinancialDataClassDTO> result = this.financialDataOp.getFinancialDataSetForAssetClass(request);
//		return new GenericResponse<List<FinancialDataClassDTO>>(1, result);
//	}
	
//	@RequestMapping("/findLastFinancialDataForAsset")
//	@ResponseBody
//	public GenericResponse<?> findLastFinancialDataForAsset(@Valid @RequestBody DataRequestDTO request) {
//		FinancialDataDTO result = this.financialDataOp.findLast(request);
//		if(result == null) {
//			return new GenericResponse<String>(0, ControllerConstants.NO_RESULTS);
//		}
//		return new GenericResponse<FinancialDataDTO>(1, result);
//	}
	
/************************************************************************************************
 * 										Test Method												*
 * ******************************************************************************************** *	
 *  @RequestMapping("/updateFinancialDataSet")													*
 *	@ResponseBody																				*
 *	public GenericResponse<?> updateFinancialDataSet() {										*
 *		this.quandlOp.updateFinancialDataSet();													*
 *		return new GenericResponse<String>(1, ControllerConstants.DONE);						*
 *	}																							*
 ************************************************************************************************/
	
/************************************************************************************************
 * 										Test Method												*
 * ******************************************************************************************** *	
 *  @RequestMapping("/initializeFinancialDataSet")												*
 *	@ResponseBody																				*
 *	public GenericResponse<?> initializeFinancialDataSet() {									*
 *		this.quandlOp.initializeFinancialDataSet();												*
 *		return new GenericResponse<String>(1, ControllerConstants.DONE);						*
 *	}																							*
 ************************************************************************************************/

}
