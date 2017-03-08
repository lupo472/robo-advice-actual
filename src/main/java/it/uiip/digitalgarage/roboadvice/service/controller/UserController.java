package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class UserController extends AbstractController {
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
		if(!this.userOp.isRegistered(userDTO.getEmail())) {
			UserLoggedDTO registered = this.userOp.registerUser(userDTO);
			return new GenericResponse<UserLoggedDTO>(1, registered);
		}
		return new GenericResponse<String>(0, ControllerConstants.EMAIL_ALREADY_REGISTERED);		
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public GenericResponse<?> loginUser(@Valid @RequestBody UserDTO userDTO) {
		if(!this.userOp.isRegistered(userDTO.getEmail())) {
			return new GenericResponse<String>(0, ControllerConstants.EMAIL_NOT_REGISTERED);
		}
		UserLoggedDTO logged = this.userOp.loginUser(userDTO);
		if(logged == null) {
			return new GenericResponse<String>(0, ControllerConstants.WRONG_PASSWORD);
		}
		return new GenericResponse<UserLoggedDTO>(1, logged);
	}
	
}