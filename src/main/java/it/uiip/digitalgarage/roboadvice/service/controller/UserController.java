package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/roboadvice")
public class UserController extends AbstractController {
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
		if(!this.userOp.isRegistered(userDTO.getEmail())) {
			UserRegisteredDTO registered = this.userOp.registerUser(userDTO);
			return new GenericResponse<UserRegisteredDTO>(1, registered);
		}
		return new GenericResponse<String>(0, ControllerConstants.EMAIL_ALREADY_REGISTERED);		
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public GenericResponse<?> loginUser(@Valid @RequestBody UserDTO userDTO) {
		if(!this.userOp.isRegistered(userDTO.getEmail())) {
			return new GenericResponse<String>(0, ControllerConstants.EMAIL_NOT_REGISTERED);
		}
		LoginDTO login = this.userOp.loginUser(userDTO);
		if(login == null) {
			return new GenericResponse<String>(0, ControllerConstants.WRONG_PASSWORD);
		}
		String secretKey = "inglouriousBasterds";
		String token = Jwts.builder().setSubject(userDTO.getEmail()).claim("role", "USER").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		login.setToken(token);
		return new GenericResponse<LoginDTO>(1, login);
	}
	
}