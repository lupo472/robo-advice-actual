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

    @RequestMapping("/getUserCurrentPortfolio")
    @ResponseBody
    public GenericResponse<?> getUserCurrentPortfolio(Authentication auth) {
        PortfolioDTO result = this.portfolioOp.getUserCurrentPortfolio(auth);
        if(result == null) {
    		return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
    	}
        return new GenericResponse<PortfolioDTO>(1, result);
    }

/************************************************************************************************
 * 										Test Method												*
 * ******************************************************************************************** *
 *   @RequestMapping("/createUserPortfolio")                                                    *
 *   @ResponseBody                                                                              *
 *   public GenericResponse<?> createUserPortfolio(Authentication auth) {                       *
 *       boolean done = this.portfolioOp.createUserPortfolio(auth);                             *
 *   	if(done) {                                                                              *
 *   		return new GenericResponse<String>(1, ControllerConstants.DONE);                    *
 *   	}                                                                                       *
 *   	return new GenericResponse<String>(0, ControllerConstants.PROBLEM);                     *
 *   }                                                                                          *
 *************************************************************************************************/

/************************************************************************************************
 * 										Test Method												*
 * ******************************************************************************************** *
 *   @RequestMapping("/computeUserPortfolio")                                                   *
 *   @ResponseBody                                                                              *
 *   public GenericResponse<?> computeUserPortfolio(Authentication auth) {                      *
 *   	boolean done = this.portfolioOp.computeUserPortfolio(auth);                             *
 *   	if(done) {                                                                              *
 *   		return new GenericResponse<String>(1, ControllerConstants.DONE);                    *
 *   	}                                                                                       *
 *   	return new GenericResponse<String>(0, ControllerConstants.PROBLEM);                     *
 *   }                                                                                          *
 *************************************************************************************************/

    @RequestMapping("/getUserPortfolioPeriod")
    @ResponseBody
    public GenericResponse<?> getUserPortfolioPeriod(@Valid @RequestBody PeriodRequestDTO request, Authentication auth) {
        List<PortfolioDTO> result = this.portfolioOp.getUserPortfolioPeriod(request, auth);
        if(result == null) {
            return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
        }
        return new GenericResponse<List<PortfolioDTO>>(1,result);
    }

/****************************************************************************************************************
 * 										Deprecated Method								                        *
 * ************************************************************************************************************ *
 *   @RequestMapping("/getUserPortfolioDate")                                                                   *
 *   @ResponseBody                                                                                              *
 *   public GenericResponse<?> getUserPortfolioDate(@Valid @RequestBody PortfolioRequestForDateDTO request){    *
 *       PortfolioDTO result = this.portfolioOp.getUserPortfolioDate(request);                                  *
 *       if(result == null) {                                                                                   *
 *           return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);                        *
 *       }                                                                                                      *
 *       return new GenericResponse<PortfolioDTO>(1, result);                                                   *
 *   }                                                                                                          *
 ********************************************************************************************************************/
}