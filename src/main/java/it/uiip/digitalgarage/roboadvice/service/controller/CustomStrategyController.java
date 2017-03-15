package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
public class CustomStrategyController extends AbstractController {

	@RequestMapping("/setCustomStrategy")
	@ResponseBody
    public GenericResponse<?> setCustomStrategy(@Valid @RequestBody CustomStrategyDTO request, Authentication auth){
		boolean response = this.customStrategyOp.setCustomStrategy(request, auth);
		if(!response){
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<String>(1, ControllerConstants.DONE);
    }
    
    @RequestMapping("/getActiveUserCustomStrategy")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategyActive(@Valid @RequestBody UserRegisteredDTO user, Authentication request){
    	CustomStrategyResponseDTO result = this.customStrategyOp.getActiveUserCustomStrategy(user);
    	System.out.println(request.getPrincipal().toString());
    	if(result == null) {
    		return new GenericResponse<String>(0, ControllerConstants.ANY_ACTIVE_STRATEGY);
    	}
    	return new GenericResponse<CustomStrategyResponseDTO>(1, result);
    }
    
//  @RequestMapping("/getUserCustomStrategySet")
//  @ResponseBody
//  public GenericResponse<?> getUserCustomStrategySet(@Valid @RequestBody UserRegisteredDTO user){
//  	List<CustomStrategyResponseDTO> result = this.customStrategyOp.getUserCustomStrategySet(user);
//  	if(result.isEmpty()) {
//  		return new GenericResponse<String>(0, ControllerConstants.ANY_STRATEGY);
//  	}
//      return new GenericResponse<List<CustomStrategyResponseDTO>>(1, result);
//  }


}
