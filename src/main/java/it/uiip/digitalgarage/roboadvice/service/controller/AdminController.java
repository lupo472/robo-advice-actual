package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.PasswordDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RequestMapping("/admin")
@RestController
public class AdminController extends AbstractController {

	private static String password = "5167477de388c8f1564f46d332567cf0d41fc77436dba7434d5bd25b18be2e77";

	@RequestMapping("/scheduleTask")
	@ResponseBody
	public GenericResponse<?> scheduleTask(@RequestBody @Valid PasswordDTO request) {
		if(HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.schedulingOp.scheduleTask();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

	@RequestMapping("/updateFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> updateFinancialDataSet(@RequestBody @Valid PasswordDTO request) {
		if(HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.quandlOp.updateFinancialDataSet();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

   	@RequestMapping("/initializeFinancialDataSet")
 	@ResponseBody
 	public GenericResponse<?> initializeFinancialDataSet(@RequestBody @Valid PasswordDTO request) {
		if(HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.quandlOp.initializeFinancialDataSet();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
 	}

}
