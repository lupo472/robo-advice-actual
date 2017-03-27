package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
public class ForecastingController extends AbstractController {

	@RequestMapping("/getForecast")
	@ResponseBody
	public GenericResponse<?> getForecast(@Valid @RequestBody PeriodRequestDTO period) {
		List<FinancialDataDTO> result = this.forecastingOp.getForecast(period);
		if(result == null) {
			return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
		}
		return new GenericResponse<List<FinancialDataDTO>>(1, result);
	}


}
