package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/roboadvice")
@RestController
public class CapitalController extends AbstractController {
	
	@RequestMapping("/addCapital")
    @ResponseBody
	public GenericResponse<?> addCapital(@Valid @RequestBody CapitalDTO capital, Authentication auth) {
		boolean done = this.capitalOp.addCapital(capital, auth);
		if(done) {
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		
	}
	
	@RequestMapping("/getCurrentCapital")
    @ResponseBody
	public GenericResponse<?> getCurrentCapital(Authentication auth) {
		CapitalResponseDTO result = this.capitalOp.getCurrentCapital(auth);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.ANY_CAPITAL);
		}
		return new GenericResponse<CapitalResponseDTO>(1, result);
	}

	@RequestMapping("/computeCapital")
	@ResponseBody
	public GenericResponse<?> computeCapital(@Valid @RequestBody UserRegisteredDTO user){
		boolean done = this.capitalOp.computeCapital(user);
		if(!done) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<String>(1, ControllerConstants.DONE);
	}

	@RequestMapping("/getCapitalPeriod")
	@ResponseBody
	public GenericResponse<?> getCapitalPeriod(@Valid @RequestBody DataRequestDTO request) {
		List<CapitalResponseDTO> result = this.capitalOp.getCapitalPeriod(request);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.ANY_CAPITAL);
		}
		return new GenericResponse<List<CapitalResponseDTO>>(1, result);
	}
	
}
