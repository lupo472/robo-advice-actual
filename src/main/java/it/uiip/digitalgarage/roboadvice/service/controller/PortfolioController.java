package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
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
    public GenericResponse<?> getUserCurrentPortfolio(@Valid @RequestBody UserLoggedDTO user) {
        this.portfolioOp = new PortfolioOperator(this.portfolioRep);
        PortfolioDTO result = this.portfolioOp.getUserCurrentPortfolio(user);
        if(result == null) {
    		return new GenericResponse<String>(0, "The portfolio of this user is empty");
    	}
        return new GenericResponse<PortfolioDTO>(1, result);
    }

    @RequestMapping("/getUserPortfolioPeriod")
    @ResponseBody
    public GenericResponse<?> getUserPortfolioPeriod(@Valid @RequestBody PortfolioRequestDTO dto) {
        this.portfolioOp = new PortfolioOperator(this.portfolioRep);
        List<PortfolioDTO> result = this.portfolioOp.getUserPortfolioPeriod(dto);
        if(result == null) {
            return new GenericResponse<String>(0, "The portfolio of this user is empty");
        }
        return new GenericResponse<List<PortfolioDTO>>(1,result);
    }

}
