package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Period;
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
	public GenericResponse<?> getDemo(@Valid @RequestBody Period period) {

		return null;
	}

}
