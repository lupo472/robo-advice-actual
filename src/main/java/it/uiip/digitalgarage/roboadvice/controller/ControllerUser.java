package it.uiip.digitalgarage.roboadvice.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.model.User;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;

@RestController
public class ControllerUser {

	@Autowired
	private UserRepository daoUser;
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public String registerUser(String email, String password) {
	    User user = new User();
	    user.setEmail(email);
	    user.setPassword(password);
	    user.setDate(LocalDate.now());
		daoUser.save(user);
		return "registrazione";
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public String loginUser(String email, String password) {
	    return "login";
	}
	
}
