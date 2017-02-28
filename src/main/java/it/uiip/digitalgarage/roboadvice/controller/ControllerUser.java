package it.uiip.digitalgarage.roboadvice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerUser {

	@RequestMapping("/registerUser")
	@ResponseBody
	public String registerUser(String email, String password) {
	    return "registrazione";
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public String loginUser(String email, String password) {
	    return "login";
	}
	
}
