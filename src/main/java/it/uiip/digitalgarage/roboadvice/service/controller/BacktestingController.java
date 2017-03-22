package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.BacktestingDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
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
		return new GenericResponse<List<PortfolioDTO>>(1, result);
	}

	//TODO remove test
//	@RequestMapping("/prova")
//	@ResponseBody
//	public List<BigDecimal> prova(Authentication auth) {
//		List<BigDecimal> result = this.financialDataOp.getFinanacialDataAsset();
//		return result;
//	}

}
