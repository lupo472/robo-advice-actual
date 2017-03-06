package it.uiip.digitalgarage.roboadvice.service.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.CapitalOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class CapitalController extends AbstractController {
	
	@RequestMapping("/addCapital")
    public GenericResponse<?> addCapital(@Valid @RequestBody CapitalDTO capital){
		this.capitalOp = new CapitalOperator(this.capitalRep);
		this.capitalOp.addCapital(capital);
		return new GenericResponse<String>(1, "done");
	}

}
