package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
public class PortfolioController extends AbstractController {

    @RequestMapping("/getUserCurrentPortfolio")
    @ResponseBody
    public GenericResponse<?> getUserCurrentPortfolio(@Valid @RequestBody UserLoggedDTO user) {
        this.portfolioOp = new PortfolioOperator(this.portfolioRep);
        PortfolioDTO result = this.portfolioOp.getUserCurrentPortfolio(user);
        if(result == null) {
    		return new GenericResponse<String>(0, "The portfolio of this user is empty");
    	}
        return new GenericResponse<PortfolioDTO>(1, result);
    }
    
    @RequestMapping("/createUserPortfolio")
    @ResponseBody
    public GenericResponse<?> createUserPortfolio(@Valid @RequestBody UserLoggedDTO user) {
    	this.portfolioOp = new PortfolioOperator(this.portfolioRep, this.capitalRep, this.customStrategyRep, this.assetRep, this.financialDataRep);
    	boolean done = this.portfolioOp.createUserPortfolio(user);
    	if(done) {
    		return new GenericResponse<String>(1, "done");
    	}
    	return new GenericResponse<String>(0, "A problem occurred");
    }

}
