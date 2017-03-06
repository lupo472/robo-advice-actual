package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.CustomStrategyOperator;

import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
public class CustomStrategyController extends AbstractController {

    @RequestMapping("/getUserCustomStrategySet")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategySet(@Valid @RequestBody UserLoggedDTO user){
    	this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);
    	List<CustomStrategyDTO> customStrategies = this.customStrategyOp.getUserCustomStrategies(user);

        return new GenericResponse<List<CustomStrategyDTO>>(1,customStrategies);
    }

    @RequestMapping("/setCustomStrategy")
    public GenericResponse<?> setCustomStrategy(@Valid @RequestBody CustomStrategyRequestDTO request){

            this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);
            this.customStrategyOp.setCustomStrategy(request);

            return new GenericResponse<String>(1, "Done");

    }

    @RequestMapping("/getUserCustomStrategyActive")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategyActive(@Valid @RequestBody UserLoggedDTO user){

        this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);

        List<CustomStrategyDTO> customStrategyActive = this.customStrategyOp.getUserCustomStrategyActive(user);

        return new GenericResponse<List<CustomStrategyDTO>>(1, customStrategyActive);

    }


}
