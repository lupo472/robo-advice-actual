package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.CustomStrategyOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
public class CustomStrategyController extends AbstractController {

	@RequestMapping("/setCustomStrategy")
	@ResponseBody
    public GenericResponse<?> setCustomStrategy(@Valid @RequestBody CustomStrategyRequestDTO request){
		this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep, this.userRep);
		this.customStrategyOp.setCustomStrategy(request);
		return new GenericResponse<String>(1, "Done");
    }
    
    @RequestMapping("/getUserCustomStrategySet")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategySet(@Valid @RequestBody UserLoggedDTO user){
    	this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);
    	List<CustomStrategyDTO> result = this.customStrategyOp.getUserCustomStrategySet(user);
    	if(result == null) {
    		return new GenericResponse<String>(0, "This user doesn't have any strategy");
    	}
        return new GenericResponse<List<CustomStrategyDTO>>(1, result);
    }

    @RequestMapping("/getActiveUserCustomStrategy")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategyActive(@Valid @RequestBody UserLoggedDTO user){
    	this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);
    	CustomStrategyDTO result = this.customStrategyOp.getActiveUserCustomStrategy(user);
    	if(result == null) {
    		return new GenericResponse<String>(0, "This user doesn't have any active strategy");
    	}
    	return new GenericResponse<CustomStrategyDTO>(1, result);
    }


}
