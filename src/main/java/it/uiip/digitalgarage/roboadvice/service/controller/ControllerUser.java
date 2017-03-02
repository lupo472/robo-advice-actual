package it.uiip.digitalgarage.roboadvice.service.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;

import javax.validation.Valid;

import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class ControllerUser {

	@Autowired
	private UserRepository daoUser;
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@Valid @RequestBody UserDTO userDTO) {


			if (daoUser.findByEmail(userDTO.getEmail()) == null) {
				UserEntity user = new UserEntity();
				user.setEmail(userDTO.getEmail());

				String password = userDTO.getPassword();

				//TODO Remove comment in production phase
				//password = HashFunction.hashStringSHA256(password);

				user.setPassword(password);
				user.setDate(LocalDate.now());
				user = daoUser.save(user);
				return new GenericResponse<UserEntity>(1, user);
			}
			return new GenericResponse<String>(0, "Email Already Registered");

	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public GenericResponse<?> loginUser(@Valid @RequestBody UserDTO userDTO) {
		UserEntity user = daoUser.findByEmail(userDTO.getEmail());
		if(user == null) {
			return new GenericResponse<String>(0, "Email not registered");
		}

		//TODO Remove comment in production phase
		//String userDTOPassword = HashFunction.hashStringSHA256(userDTO.getPassword());
		//TODO Comment in production
		String userDTOPassword = userDTO.getPassword();

		if(user.getPassword().equals(userDTOPassword)) {
			return new GenericResponse<UserEntity>(1, user);
		}
		return new GenericResponse<String>(0, "Wrong Password"); 
	}
	
}
