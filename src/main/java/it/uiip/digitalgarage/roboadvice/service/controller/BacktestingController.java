package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.BacktestingDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This class contains the Rest-APIs related to the Backtesting.
 *
 * @author Cristian Laurini
 */
@CrossOrigin("*")
@RestController
public class BacktestingController extends AbstractController {

	/**
	 * This method allows to get the Backtesting.
	 * The related API is <b>/getBacktesting</b>
	 *
	 * @param request 	BacktestingDTO contains the capital, the period and the strategy.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 and a message if the selected strategy is not applicable
	 * 					for the selected period, or response 1 and a List of PortfolioDTO that represents the
	 * 					result of the computation of the Backtesting.
	 */
	@RequestMapping("/getBacktesting")
	@ResponseBody
	public GenericResponse<?> getBacktesting(@Valid @RequestBody BacktestingDTO request, Authentication auth) {
		Long start = System.currentTimeMillis();
		List<PortfolioDTO> result = this.backtestingOp.getBacktesting(request, auth);
		Long end = System.currentTimeMillis();
		System.out.println("GetBacktesting in " + (end - start) + " ms");
		if (result == null) {
			return new GenericResponse<String>(0, ControllerConstants.NOT_APPLICABLE);
		}
		return new GenericResponse<List<PortfolioDTO>>(1, result);
	}

	/**
	 * This method allows to get the minimum date for the computation of the Backtesting.
	 * The related API is <b>/getMinimumBacktestingDate</b>
	 *
	 * @param request 	CustomStrategyDTO contains the selected strategy.
	 * @return			GenericResponse with response 1 and a String containing a date.
	 */
	@RequestMapping("/getMinimumBacktestingDate")
	@ResponseBody
	public GenericResponse<?> getMinimumBacktestingDate(@Valid @RequestBody CustomStrategyDTO request) {
		Long start = System.currentTimeMillis();
		String date = this.backtestingOp.getMinimumBacktestingDate(request);
		Long end = System.currentTimeMillis();
		System.out.println("GeMinimumBacktestingDate in " + (end - start) + " ms");
		return new GenericResponse<String>(1, date);
	}

}
