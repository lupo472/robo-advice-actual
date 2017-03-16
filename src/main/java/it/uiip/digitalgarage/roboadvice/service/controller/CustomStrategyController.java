package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.security.core.Authentication;
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
    public GenericResponse<?> setCustomStrategy(@Valid @RequestBody CustomStrategyDTO request, Authentication auth){
		boolean response = this.customStrategyOp.setCustomStrategy(request, auth);
		if(!response){
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<String>(1, ControllerConstants.DONE);
    }
    
    @RequestMapping("/getActiveStrategy")
    @ResponseBody
    public GenericResponse<?> getActiveStrategy(Authentication auth){
    	CustomStrategyResponseDTO result = this.customStrategyOp.getActiveStrategy(auth);
    	if(result == null) {
    		return new GenericResponse<String>(0, ControllerConstants.ANY_ACTIVE_STRATEGY);
    	}
    	return new GenericResponse<CustomStrategyResponseDTO>(1, result);
    }
    
    @RequestMapping("/getCustomStrategyHistory")
    @ResponseBody
    public GenericResponse<?> getCustomStrategyHistory(@RequestBody @Valid PeriodRequestDTO period, Authentication auth) {
    	List<CustomStrategyResponseDTO> result = this.customStrategyOp.getCustomStrategySet(auth, period.getPeriod());
    	if(result.isEmpty()) {
    		return new GenericResponse<String>(0, ControllerConstants.ANY_STRATEGY_IN_PERIOD);
    	}
    	return new GenericResponse<List<CustomStrategyResponseDTO>>(1, result);
    }
 
}
