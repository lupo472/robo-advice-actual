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

@CrossOrigin("*")
@RestController
public class ForecastingController extends AbstractController {

	@RequestMapping("/getForecast")
	@ResponseBody
	public GenericResponse<?> getForecast(@Valid @RequestBody PeriodDTO period) {
		List<FinancialDataDTO> result = this.forecastingOp.getForecast(period);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<List<FinancialDataDTO>>(1, result);
	}

	@RequestMapping("/getDemo")
	@ResponseBody
	public GenericResponse<?> getDemo(@Valid @RequestBody PeriodDTO period, Authentication auth) {
		List<PortfolioDTO> result = this.forecastingOp.getDemo(period, auth);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<List<PortfolioDTO>>(1, result);
	}

	@RequestMapping("/getAdvice")
	@ResponseBody
	public GenericResponse<?> getAdvice(@Valid @RequestBody PeriodDTO period, Authentication auth) {
		DefaultStrategyDTO defaultStrategyDTO = this.adviceOp.getAdvice(period, auth);
		if(defaultStrategyDTO == null) {
			return new GenericResponse<String>(0, "Your current strategy is ok"); //TODO Change message
		}
		return new GenericResponse<DefaultStrategyDTO>(1, defaultStrategyDTO);
	}
}
