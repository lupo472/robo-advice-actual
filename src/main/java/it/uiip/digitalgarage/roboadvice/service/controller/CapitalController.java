package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.CapitalOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class CapitalController extends AbstractController {
	
	@RequestMapping("/addCapital")
    @ResponseBody
	public GenericResponse<?> addCapital(@Valid @RequestBody CapitalDTO capital) {
		this.capitalOp = new CapitalOperator(this.capitalRep);
		this.capitalOp.addCapital(capital);
		return new GenericResponse<String>(1, "done");
	}
	
	@RequestMapping("/getCurrentCapital")
    @ResponseBody
	public GenericResponse<?> getCurrentCapital(@Valid @RequestBody UserLoggedDTO user){
		this.capitalOp = new CapitalOperator(this.capitalRep);
		CapitalResponseDTO result = this.capitalOp.getCurrentCapital(user);
		if(result == null) {
			return new GenericResponse<String>(0, "This user doesn't have any capital");
		}
		return new GenericResponse<CapitalResponseDTO>(1, result);
	}

	@RequestMapping("/computeCapital")
	@ResponseBody
	public GenericResponse<?> computeCapital(@Valid @RequestBody UserLoggedDTO user){
		this.capitalOp = new CapitalOperator(this.capitalRep, this.portfolioRep, this.financialDataRep);
		boolean done = this.capitalOp.computeCapital(user);
		if(!done) {
			return new GenericResponse<String>(0, "A problem occurred");
		}
		return new GenericResponse<String>(1, "done");
	}
}
