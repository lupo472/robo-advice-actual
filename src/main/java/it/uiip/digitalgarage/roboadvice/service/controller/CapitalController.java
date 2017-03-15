package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import it.uiip.digitalgarage.roboadvice.service.dto.PeriodRequestDTO;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

import java.util.List;

@CrossOrigin("*")
@RestController
public class CapitalController extends AbstractController {
	
	@RequestMapping("/addCapital")
    @ResponseBody
	public GenericResponse<?> addCapital(@Valid @RequestBody CapitalRequestDTO capital, Authentication auth) {
		boolean done = this.capitalOp.addCapital(capital, auth);
		if(done) {
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
	}
	
	@RequestMapping("/getCurrentCapital")
    @ResponseBody
	public GenericResponse<?> getCurrentCapital(Authentication auth) {
		CapitalDTO result = this.capitalOp.getCurrentCapital(auth);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.ANY_CAPITAL);
		}
		return new GenericResponse<CapitalDTO>(1, result);
	}

	@RequestMapping("/getCapitalForPeriod")
	@ResponseBody
	public GenericResponse<?> getCapitalForPeriod(@Valid @RequestBody PeriodRequestDTO request, Authentication auth) {
		List<CapitalDTO> result = this.capitalOp.getCapitalPeriod(request, auth);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.ANY_CAPITAL);
		}
		return new GenericResponse<List<CapitalDTO>>(1, result);
	}
	
/************************************************************************************************
 * 										Test Method												*
 * ******************************************************************************************** *	
 *  @RequestMapping("/computeCapital")															*
 *	@ResponseBody																				*
 *	public GenericResponse<?> computeCapital(@Valid @RequestBody UserRegisteredDTO user){		*
 *		boolean done = this.capitalOp.computeCapital(user);										*
 *		if(!done) {																				*
 *			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);					*	
 *		}																						*
 *		return new GenericResponse<String>(1, ControllerConstants.DONE);						*
 *	}																							*
 ************************************************************************************************/
	
}
