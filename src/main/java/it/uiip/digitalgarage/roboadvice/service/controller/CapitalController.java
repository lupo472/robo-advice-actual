package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import org.apache.tomcat.util.http.parser.Authorization;
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
	public GenericResponse<?> addCapital(@Valid @RequestBody CapitalDTO capital) {
		this.capitalOp.addCapital(capital);
		return new GenericResponse<String>(1, ControllerConstants.DONE);
	}
	
	@RequestMapping("/getCurrentCapital")
    @ResponseBody
	public GenericResponse<?> getCurrentCapital(@Valid @RequestBody UserRegisteredDTO user){
		CapitalResponseDTO result = this.capitalOp.getCurrentCapital(user);
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
