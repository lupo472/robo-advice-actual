package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.UserOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class UserController extends GenericController {
	
	private UserOperator userOperator;
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
		this.userOperator = new UserOperator(this.userRep);
		if(!this.userOperator.isRegistered(userDTO.getEmail())) {
			UserDTO registered = this.userOperator.registerUser(userDTO);
			return new GenericResponse<UserDTO>(1, registered);
		}
		return new GenericResponse<String>(0, "Email Already Registered");		
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public GenericResponse<?> loginUser(@Valid @RequestBody UserDTO userDTO) {
		this.userOperator = new UserOperator(this.userRep);
		if(!this.userOperator.isRegistered(userDTO.getEmail())) {
			return new GenericResponse<String>(0, "Email not registered");
		}
		UserDTO logged = this.userOperator.loginUser(userDTO);
		if(logged == null) {
			return new GenericResponse<String>(0, "Wrong Password");
		}
		return new GenericResponse<UserDTO>(1, logged);
	}
	
}
