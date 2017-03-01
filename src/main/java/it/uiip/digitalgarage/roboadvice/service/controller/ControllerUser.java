package it.uiip.digitalgarage.roboadvice.service.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@RestController
public class ControllerUser {

	@Autowired
	private UserRepository daoUser;
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@RequestBody UserDTO userDTO) {
	    if(daoUser.findByEmail(userDTO.getEmail()) == null) {
			UserEntity user = new UserEntity();
		    user.setEmail(userDTO.getEmail());
		    user.setPassword(userDTO.getPassword());
		    user.setDate(LocalDate.now());
			user = daoUser.save(user);
			return new GenericResponse<UserEntity>(1, user);
	    }
		return new GenericResponse<String>(0, "User Already Registered");
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public String loginUser(String email, String password) {
	    return "login";
	}
	
}
