package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.AuthDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class AuthController {

	@RequestMapping("/authenticate")
	@ResponseBody
	public GenericResponse<?> authenticate(@RequestBody @Valid AuthDTO auth) {
		return new GenericResponse<String>(1, ControllerConstants.AUTHENTICATED);
	}
		
	
}
