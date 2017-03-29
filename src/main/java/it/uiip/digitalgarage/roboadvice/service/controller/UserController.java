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

/**
 * This class contains the Rest-APIs related to the User.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@CrossOrigin("*")
@RestController
public class UserController extends AbstractController {

	/**
	 * This method allows to register a new User.
	 * The related API is <b>/registerUser</b>
	 *
	 * @param user	UserDTO contains email and password for the user that will be registered.
	 * @return		GenericResponse with response 0 if the email is already registered,
	 * 				or with response 1 instead, with the related messages.
	 */
	@RequestMapping("/registerUser")
	@ResponseBody
	public GenericResponse<?> registerUser(@Valid @RequestBody UserDTO user) {
		Long start = System.currentTimeMillis();
		boolean done = this.userOp.registerUser(user);
		Long end = System.currentTimeMillis();
		System.out.println("RegisterUser in " + (end - start) + " ms");
		if(done) {
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.EMAIL_ALREADY_REGISTERED);		
	}

	/**
	 * This method allows to login an User.
	 * The related API is <b>/loginUser</b>
	 *
	 * @param user	UserDTO contains email and password for the user that will be logged.
	 * @return		GenericResponse with 0 and a message if the email is not registered or if the password is wrong,
	 * 				or with response 1 and a LoginDTO containg the email and a token for the security.
	 */
	@RequestMapping("/loginUser")
	@ResponseBody
	public GenericResponse<?> loginUser(@Valid @RequestBody UserDTO user) {
		Long start = System.currentTimeMillis();
		if(!this.userOp.isRegistered(user.getEmail())) {
			return new GenericResponse<String>(0, ControllerConstants.EMAIL_NOT_REGISTERED);
		}
		LoginDTO login = this.userOp.loginUser(user);
		Long end = System.currentTimeMillis();
		System.out.println("LoginUser in " + (end - start) + " ms");
		if(login == null) {
			return new GenericResponse<String>(0, ControllerConstants.WRONG_PASSWORD);
		}
		return new GenericResponse<LoginDTO>(1, login);
	}
	
}