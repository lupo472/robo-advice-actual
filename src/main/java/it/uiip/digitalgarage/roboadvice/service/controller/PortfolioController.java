package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.*;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
public class PortfolioController extends AbstractController {

    @RequestMapping("/getCurrentPortfolio")
    @ResponseBody
    public GenericResponse<?> getCurrentPortfolio(Authentication auth) {
        PortfolioDTO result = this.portfolioOp.getCurrentPortfolio(auth);
        if(result == null) {
    		return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
    	}
        return new GenericResponse<PortfolioDTO>(1, result);
    }
    
    @RequestMapping("/getPortfolioForPeriod")
    @ResponseBody
    public GenericResponse<?> getPortfolioForPeriod(@Valid @RequestBody PeriodRequestDTO request, Authentication auth) {
        List<PortfolioDTO> result = this.portfolioOp.getPortfolioForPeriod(request, auth);
        if(result == null) {
            return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
        }
        return new GenericResponse<List<PortfolioDTO>>(1, result);
    }

/************************************************************************************************
 * 										Test Method												*
 * **********************************************************************************************
 *   @RequestMapping("/createUserPortfolio")                                                    *
 *   @ResponseBody                                                                              *
 *   public GenericResponse<?> createUserPortfolio(Authentication auth) {                       *
 *       boolean done = this.portfolioOp.createUserPortfolio(auth);                             *
 *   	if(done) {                                                                              *
 *   		return new GenericResponse<String>(1, ControllerConstants.DONE);                    *
 *   	}                                                                                       *
 *   	return new GenericResponse<String>(0, ControllerConstants.PROBLEM);                     *
 *   }                                                                                          *
 ************************************************************************************************/

/************************************************************************************************
 * 										Test Method												*
 ************************************************************************************************
 *   @RequestMapping("/computeUserPortfolio")                                                   *
 *   @ResponseBody                                                                              *
 *   public GenericResponse<?> computeUserPortfolio(Authentication auth) {                      *
 *   	boolean done = this.portfolioOp.computeUserPortfolio(auth);                             *
 *   	if(done) {                                                                              *
 *   		return new GenericResponse<String>(1, ControllerConstants.DONE);                    *
 *   	}                                                                                       *
 *   	return new GenericResponse<String>(0, ControllerConstants.PROBLEM);                     *
 *   }                                                                                          *
 ************************************************************************************************/                                                                                                          

}