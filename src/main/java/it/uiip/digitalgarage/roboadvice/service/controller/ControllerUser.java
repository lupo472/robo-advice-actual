package it.uiip.digitalgarage.roboadvice.service.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.model.User;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.model.GenericResponse;

@RestController
public class ControllerUser {

	@Autowired
	private UserRepository daoUser;
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(String email, String password) {
	    if(daoUser.findByEmail(email) == null) {
			User user = new User();
		    user.setEmail(email);
		    user.setPassword(password);
		    user.setDate(LocalDate.now());
			user = daoUser.save(user);
			return new GenericResponse<User>(1, user);
	    }
		return new GenericResponse<String>(0, "User Already Registered");
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public String loginUser(String email, String password) {
	    return "login";
	}
	
}
