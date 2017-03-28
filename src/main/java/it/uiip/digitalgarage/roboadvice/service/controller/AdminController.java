package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.PasswordDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class contains the Rest-APIs that the admin of the system can use to force some system computations.
 * All the APIs are protected by a password.
 *
 * @author Cristian Laurini
 */
@CrossOrigin("*")
@RequestMapping("/admin")
@RestController
public class AdminController extends AbstractController {

	private static String password = "5167477de388c8f1564f46d332567cf0d41fc77436dba7434d5bd25b18be2e77";

	/**
	 * This method allows to force the nightly computation of the system.
	 * The related API is <b>/scheduleTask</b>
	 *
	 * @param 	request 	PasswordDTO containing the admin password
	 * @return				GenericResponse with response 1 if the password is correct, or response and 0 if it is not,
	 * 						with the relative messages.
	 */
	@RequestMapping("/scheduleTask")
	@ResponseBody
	public GenericResponse<?> scheduleTask(@RequestBody @Valid PasswordDTO request) {
		if (HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.schedulingOp.scheduleTask();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

	/**
	 * This method allows to force the updating the financial data.
	 * The related API is <b>/updateFinancialDataSet</b>
	 *
	 * @param 	request 	PasswordDTO containing the admin password
	 * @return				GenericResponse with response 1 if the password is correct, or response and 0 if it is not,
	 * 						with the relative messages.
	 */
	@RequestMapping("/updateFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> updateFinancialDataSet(@RequestBody @Valid PasswordDTO request) {
		if (HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.quandlOp.updateFinancialDataSet();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

	/**
	 * This method allows to force the initialization the financial data.
	 * The related API is <b>/initializeFinancialDataSet</b>
	 *
	 * @param 	request 	PasswordDTO containing the admin password
	 * @return				GenericResponse with response 1 if the password is correct, or response and 0 if it is not,
	 * 						with the relative messages.
	 */
	@RequestMapping("/initializeFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> initializeFinancialDataSet(@RequestBody @Valid PasswordDTO request) {
		if (HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.quandlOp.initializeFinancialDataSet();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

}