package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.UserOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class UserController extends GenericController {
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@Valid @RequestBody UserRequestDTO userDTO) {
		this.userOp = new UserOperator(this.userRep);
		if(!this.userOp.isRegistered(userDTO.getEmail())) {
			UserResponseDTO registered = this.userOp.registerUser(userDTO);
			return new GenericResponse<UserResponseDTO>(1, registered);
		}
		return new GenericResponse<String>(0, "Email Already Registered");		
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public GenericResponse<?> loginUser(@Valid @RequestBody UserRequestDTO userDTO) {
		this.userOp = new UserOperator(this.userRep);
		if(!this.userOp.isRegistered(userDTO.getEmail())) {
			return new GenericResponse<String>(0, "Email not registered");
		}
		UserResponseDTO logged = this.userOp.loginUser(userDTO);
		if(logged == null) {
			return new GenericResponse<String>(0, "Wrong Password");
		}
		return new GenericResponse<UserResponseDTO>(1, logged);
	}
	
}
