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
    public GenericResponse<?> getUserCurrentPortfolio(@Valid @RequestBody UserLoggedDTO user) {
        this.portfolioOp = new PortfolioOperator(this.portfolioRep);
        PortfolioDTO dto = this.portfolioOp.getUserCurrentPortfolio(user);
        return new GenericResponse<PortfolioDTO>(1,dto);
    }

}
