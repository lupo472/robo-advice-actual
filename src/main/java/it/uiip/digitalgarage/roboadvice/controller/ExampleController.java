package it.uiip.digitalgarage.roboadvice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.model.User;
import it.uiip.digitalgarage.roboadvice.persistence.idao.UserRepository;

@RestController
public class ExampleController {
	  
	  /**
	   * GET /get-by-email  --> Return the id for the user having the passed
	   * email.
	   */
	  @RequestMapping("/get")
	  @ResponseBody
	  public String getByEmail(String email) {
	    String userId = "";
	    try {
	      User user = userDao.findByEmail(email);
	      userId = String.valueOf(user.getId());
	    }
	    catch (Exception ex) {
	      return "User not found";
	    }
	    return "The user id is: " + userId;
	  }

	  @Autowired
	  private UserRepository userDao;
	  
}