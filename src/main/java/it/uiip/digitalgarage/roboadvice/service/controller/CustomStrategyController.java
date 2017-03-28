package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
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

/**
 * This class contains the Rest-APIs related to the CustomStrategies.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@CrossOrigin("*")
@RestController
public class CustomStrategyController extends AbstractController {

	/**
	 * This method allow to set a Custom Strategy for the logged user.
	 * The related API is <b>/setCustomStrategy</b>
	 *
	 * @param request	CustomStrategyDTO contains a List of AssetClassStrategyDTO that represent each Asset Class
	 *                  of the strategy with the related percentage.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 if some problem occurs, or 1 instead,
	 * 					with the related message.
	 */
	@RequestMapping("/setCustomStrategy")
	@ResponseBody
    public GenericResponse<?> setCustomStrategy(@Valid @RequestBody CustomStrategyDTO request, Authentication auth){
		boolean response = this.customStrategyOp.setCustomStrategy(request, auth);
		if(!response){
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<String>(1, ControllerConstants.DONE);
    }

	/**
	 * This method allow to get the active Custom Strategy for the logged user.
	 * The related API is <b>/getActiveStrategy</b>
	 *
	 * @param auth	Authentication for the security.
	 * @return		GenericResponse with response 0 with a message if the user doesn't have any active strategy,
	 * 				or response 1 and a CustomStrategyResponseDTO containing the active strategy of the user.
	 */
	@RequestMapping("/getActiveStrategy")
    @ResponseBody
    public GenericResponse<?> getActiveStrategy(Authentication auth){
    	CustomStrategyResponseDTO result = this.customStrategyOp.getActiveStrategy(auth);
    	if(result == null) {
    		return new GenericResponse<String>(0, ControllerConstants.ANY_ACTIVE_STRATEGY);
    	}
    	return new GenericResponse<CustomStrategyResponseDTO>(1, result);
    }

	/**
	 * This method allow to get the history of the Custom Strategies for the logged user.
	 * The related API is <b>/getCustomStrategyHistory</b>
	 *
	 * @param period	PeriodDTO contains the number of days requested.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 with a message if the user doesn't have any strategy in the
	 * 					selected period, or response 1 and a List of CustomStrategyResponseDTOs containing the
	 * 					strategies of the user in that period.
	 */
	@RequestMapping("/getCustomStrategyHistory")
    @ResponseBody
    public GenericResponse<?> getCustomStrategyHistory(@RequestBody @Valid PeriodDTO period, Authentication auth) {
    	List<CustomStrategyResponseDTO> result = this.customStrategyOp.getCustomStrategySet(auth, period.getPeriod());
    	if(result.isEmpty()) {
    		return new GenericResponse<String>(0, ControllerConstants.ANY_STRATEGY_IN_PERIOD);
    	}
    	return new GenericResponse<List<CustomStrategyResponseDTO>>(1, result);
    }
 
}
