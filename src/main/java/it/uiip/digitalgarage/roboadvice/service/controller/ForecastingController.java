package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This class contains the Rest-APIs related to Forecasting, Demo and Advice.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@CrossOrigin("*")
@RestController
public class ForecastingController extends AbstractController {

	/**
	 * This method allows to get a forecasting for the selected period.
	 * The related API is <b>/getForecast</b>
	 *
	 * @param period	PeriodDTO contains the number of days requested.
	 * @return			GenericResponse with response 0 and a message if some problem occurs, or with response 1
	 * 					and a List of FinancialDataDTOs that represent the forecasted data.
	 */
	@RequestMapping("/getForecast")
	@ResponseBody
	public GenericResponse<?> getForecast(@Valid @RequestBody PeriodDTO period) {
		List<FinancialDataDTO> result = this.forecastingOp.getForecast(period);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<List<FinancialDataDTO>>(1, result);
	}

	/**
	 * This method allows to get a Demo for the selected period on the Portfolio of the logged user.
	 * The related API is <b>/getDemo</b>
	 *
	 * @param period	PeriodDTO contains the number of days requested.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 and a message if some problem occurs, or with response 1
	 * 					and a List of PortfolioDTO that represent the demo.
	 */
	@RequestMapping("/getDemo")
	@ResponseBody
	public GenericResponse<?> getDemo(@Valid @RequestBody PeriodDTO period, Authentication auth) {
		List<PortfolioDTO> result = this.forecastingOp.getDemo(period, auth);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<List<PortfolioDTO>>(1, result);
	}

	/**
	 * This method allows to get an Advice regarding the strategy of the logged user.
	 * The related API is <b>/getAdvice</b>
	 *
	 * @param period	PeriodDTO contains the number of days requested.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 and a message if the user doesn't have a capital or if
	 * 					the strategy of the user is already OK, or with response 1 and a DefaultStrategyDTO
	 * 					that represents the advice.
	 */
	@RequestMapping("/getAdvice")
	@ResponseBody
	public GenericResponse<?> getAdvice(@Valid @RequestBody PeriodDTO period, Authentication auth) {
		if(this.capitalOp.getCurrentCapital(auth) == null) {
			return new GenericResponse<String>(0, ControllerConstants.ANY_CAPITAL);
		}
		DefaultStrategyDTO defaultStrategyDTO = this.adviceOp.getAdvice(period, auth);
		if(defaultStrategyDTO == null) {
			return new GenericResponse<String>(0, ControllerConstants.STRATEGY_OK);
		}
		return new GenericResponse<DefaultStrategyDTO>(1, defaultStrategyDTO);
	}
	
}
