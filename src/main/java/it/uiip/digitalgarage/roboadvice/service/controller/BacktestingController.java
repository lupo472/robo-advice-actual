package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.BacktestingDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
public class BacktestingController extends AbstractController {

	@RequestMapping("/getBacktesting")
	@ResponseBody
	public GenericResponse<?> getBacktesting(@Valid @RequestBody BacktestingDTO request, Authentication auth) {
		Long start = System.currentTimeMillis();
		List<PortfolioDTO> result = this.backtestingOp.getBacktesting(request, auth);
		Long end = System.currentTimeMillis();
		System.out.println((end - start) + " ms");
		if (result == null) {
			return new GenericResponse<String>(0, ControllerConstants.NOT_APPLICABLE);
		}
		return new GenericResponse<List<PortfolioDTO>>(1, result);
	}

	@RequestMapping("/getMinimumBacktestingDate")
	@ResponseBody
	public GenericResponse<?> getMinimumBacktestingDate(@Valid @RequestBody CustomStrategyDTO request) {
		String date = this.backtestingOp.getMinimumBacktestingDate(request);
		return new GenericResponse<String>(1, date);
	}

}
