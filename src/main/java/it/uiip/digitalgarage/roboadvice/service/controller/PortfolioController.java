package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioRequestForDateDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/roboadvice")
public class PortfolioController extends AbstractController {

    @RequestMapping("/getUserCurrentPortfolio")
    @ResponseBody
    public GenericResponse<?> getUserCurrentPortfolio(@Valid @RequestBody UserRegisteredDTO user) {
        PortfolioDTO result = this.portfolioOp.getUserCurrentPortfolio(user);
        if(result == null) {
    		return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
    	}
        return new GenericResponse<PortfolioDTO>(1, result);
    }
    
    @RequestMapping("/createUserPortfolio")
    @ResponseBody
    public GenericResponse<?> createUserPortfolio(@Valid @RequestBody UserRegisteredDTO user) {
    	boolean done = this.portfolioOp.createUserPortfolio(user);
    	if(done) {
    		return new GenericResponse<String>(1, ControllerConstants.DONE);
    	}
    	return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
    }
    
    @RequestMapping("/computeUserPortfolio")
    @ResponseBody
    public GenericResponse<?> computeUserPortfolio(@Valid @RequestBody UserRegisteredDTO user) {
    	boolean done = this.portfolioOp.computeUserPortfolio(user);
    	if(done) {
    		return new GenericResponse<String>(1, ControllerConstants.DONE);
    	}
    	return new GenericResponse<String>(0, ControllerConstants.PROBLEM);
    }

    @RequestMapping("/getUserPortfolioPeriod")
    @ResponseBody
    public GenericResponse<?> getUserPortfolioPeriod(@Valid @RequestBody DataRequestDTO request) {
        List<PortfolioDTO> result = this.portfolioOp.getUserPortfolioPeriod(request);
        if(result == null) {
            return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
        }
        return new GenericResponse<List<PortfolioDTO>>(1,result);
    }

    @RequestMapping("/getUserPortfolioDate")
    @ResponseBody
    public GenericResponse<?> getUserPortfolioDate(@Valid @RequestBody PortfolioRequestForDateDTO request){
        PortfolioDTO result = this.portfolioOp.getUserPortfolioDate(request);
        if(result == null) {
            return new GenericResponse<String>(0, ControllerConstants.EMPTY_PORTFOLIO);
        }
        return new GenericResponse<PortfolioDTO>(1, result);
    }

}