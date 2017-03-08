package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
public class PortfolioController extends AbstractController {

    @RequestMapping("/getUserCurrentPortfolio")
    @ResponseBody
    public GenericResponse<?> getUserCurrentPortfolio(@Valid @RequestBody UserLoggedDTO user) {
//        this.portfolioOp = new PortfolioOperator(this.portfolioRep);
        PortfolioDTO result = this.portfolioOp.getUserCurrentPortfolio(user);
        if(result == null) {
    		return new GenericResponse<String>(0, "The portfolio of this user is empty");
    	}
        return new GenericResponse<PortfolioDTO>(1, result);
    }
    
    @RequestMapping("/createUserPortfolio")
    @ResponseBody
    public GenericResponse<?> createUserPortfolio(@Valid @RequestBody UserLoggedDTO user) {
//    	this.portfolioOp = new PortfolioOperator(this.portfolioRep, this.capitalRep, this.customStrategyRep, 
//    											 this.assetRep, this.financialDataRep, this.userRep);
    	boolean done = this.portfolioOp.createUserPortfolio(user);
    	if(done) {
    		return new GenericResponse<String>(1, "done");
    	}
    	return new GenericResponse<String>(0, "A problem occurred");
    }
    
    @RequestMapping("/computeUserPortfolio")
    @ResponseBody
    public GenericResponse<?> computeUserPortfolio(@Valid @RequestBody UserLoggedDTO user) {
//    	this.portfolioOp = new PortfolioOperator(this.portfolioRep, this.capitalRep, this.customStrategyRep, 
//				 this.assetRep, this.financialDataRep, this.userRep);
    	boolean done = this.portfolioOp.computeUserPortfolio(user);
    	if(done) {
    		return new GenericResponse<String>(1, "done");
    	}
    	return new GenericResponse<String>(0, "A problem occurred");
    }

    @RequestMapping("/getUserPortfolioPeriod")
    @ResponseBody
    public GenericResponse<?> getUserPortfolioPeriod(@Valid @RequestBody DataRequestDTO request) {
//        this.portfolioOp = new PortfolioOperator(this.portfolioRep);
        List<PortfolioDTO> result = this.portfolioOp.getUserPortfolioPeriod(request);
        if(result == null) {
            return new GenericResponse<String>(0, "The portfolio of this user is empty");
        }
        return new GenericResponse<List<PortfolioDTO>>(1,result);
    }

    @RequestMapping("/getUserPortfolioDate")
    @ResponseBody
    public GenericResponse<?> getUserPortfolioDate(@Valid @RequestBody PortfolioRequestDTO request){
//        this.portfolioOp = new PortfolioOperator(this.portfolioRep);
        PortfolioDTO result = this.portfolioOp.getUserPortfolioDate(request);
        if(result == null) {
            return new GenericResponse<String>(0,"The portfolio of this user at the date selected is empty");
        }
        return new GenericResponse<PortfolioDTO>(1, result);
    }

}
