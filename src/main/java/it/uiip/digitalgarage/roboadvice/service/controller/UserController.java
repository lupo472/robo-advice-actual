package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class UserController extends AbstractController {
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@Valid @RequestBody UserDTO user) {
		boolean done = this.userOp.registerUser(user);
		if(done) {
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.EMAIL_ALREADY_REGISTERED);		
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public GenericResponse<?> loginUser(@Valid @RequestBody UserDTO user) {
		if(!this.userOp.isRegistered(user.getEmail())) {
			return new GenericResponse<String>(0, ControllerConstants.EMAIL_NOT_REGISTERED);
		}
		LoginDTO login = this.userOp.loginUser(user);
		if(login == null) {
			return new GenericResponse<String>(0, ControllerConstants.WRONG_PASSWORD);
		}
		return new GenericResponse<LoginDTO>(1, login);
	}
	
}