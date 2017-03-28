package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.*;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This class contains the Rest-APIs related to the Portfolio.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@CrossOrigin("*")
@RestController
public class PortfolioController extends AbstractController {

	/**
	 * This method allows to get the current Portfolio for the logged user.
	 * The related API is <b>/getPortfolio</b>
	 *
	 * @param auth	Authentication for the security.
	 * @return		GenericResponse with response 0 and a message if the portfolio of the user is empty,
	 * 				or response 1 and the PortfolioDTO representing the current Portfolio of the user.
	 */
    @RequestMapping("/getCurrentPortfolio")
    @ResponseBody
    public GenericResponse<?> getCurrentPortfolio(Authentication auth) {
        PortfolioDTO result = this.portfolioOp.getCurrentPortfolio(auth);
        if(result == null) {
    		return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
    	}
        return new GenericResponse<PortfolioDTO>(1, result);
    }

	/**
	 * This method allows to get the history of the Portfolio of the logged user.
	 * The related API is <b>/getPortfolioForPeriod</b>
	 *
	 * @param request	PeriodDTO contains the number of days requested.
	 * @param auth		Authentication for the security.
	 * @return			GenericResponse with response 0 and a message if the portfolio of the user is empty,
	 * 					or response 1 and a List of PortfolioDTOs.
	 */
	@RequestMapping("/getPortfolioForPeriod")
    @ResponseBody
    public GenericResponse<?> getPortfolioForPeriod(@Valid @RequestBody PeriodDTO request, Authentication auth) {
        List<PortfolioDTO> result = this.portfolioOp.getPortfolioForPeriod(request, auth);
        if(result == null) {
            return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
        }
        return new GenericResponse<List<PortfolioDTO>>(1, result);
    }

}