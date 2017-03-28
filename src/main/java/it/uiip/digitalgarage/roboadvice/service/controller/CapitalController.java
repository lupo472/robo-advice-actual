package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

import java.util.List;

/**
 * This class contains the Rest-APIs related to the Capital.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@CrossOrigin("*")
@RestController
public class CapitalController extends AbstractController {

	/**
	 * This method allows to add a capital for the logged user.
	 * The related API is <b>/addCapital</b>
	 *
	 * @param capital	CapitalRequestDTO contains the amount of capital to add.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 if some problem occurs, or 1 instead,
	 * 					with the related message.
	 */
	@RequestMapping("/addCapital")
    @ResponseBody
	public GenericResponse<?> addCapital(@Valid @RequestBody CapitalRequestDTO capital, Authentication auth) {
		boolean done = this.capitalOp.addCapital(capital, auth);
		if(done) {
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
	}

	/**
	 * This method allows to get the current capital for the logged user.
	 * The related API is <b>/getCurrentCapital</b>
	 *
	 * @param auth	Authentication for the security.
	 * @return		GenericResponse with response 0 and a message if the user doesn't have any capital,
	 * 				or response 1 and CapitalDTO that contains the amount of money of the user and
	 * 				the related date.
	 */
	@RequestMapping("/getCurrentCapital")
    @ResponseBody
	public GenericResponse<?> getCurrentCapital(Authentication auth) {
		CapitalDTO result = this.capitalOp.getCurrentCapital(auth);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.ANY_CAPITAL);
		}
		return new GenericResponse<CapitalDTO>(1, result);
	}

	/**
	 * This method allows to get history of the capital for the logged user.
	 * The related API is <b>/getCapitalForPeriod</b>
	 *
	 * @param request	PeriodDTO contains the number of days requested.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 and a message if the user doesn't have any capital,
	 * 					or response 1 and a List of CapitalDTOs containing the amount of money and the
	 * 					related date.
	 */
	@RequestMapping("/getCapitalForPeriod")
	@ResponseBody
	public GenericResponse<?> getCapitalForPeriod(@Valid @RequestBody PeriodDTO request, Authentication auth) {
		List<CapitalDTO> result = this.capitalOp.getCapitalPeriod(request, auth);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.ANY_CAPITAL);
		}
		return new GenericResponse<List<CapitalDTO>>(1, result);
	}

}
