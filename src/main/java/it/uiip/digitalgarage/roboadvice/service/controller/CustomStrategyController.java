package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.CustomStrategyOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;

import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
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

        if(customStrategies == null) return new GenericResponse<String>(0,"Failure");

        return new GenericResponse<List<CustomStrategyDTO>>(1,customStrategies);
    }

    @RequestMapping("/setCustomStrategy")
    public GenericResponse setCustomStrategy(@Valid @RequestBody List<CustomStrategyDTO> customStrategies){

            this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);

            List<CustomStrategyDTO> createdStrategiesSaved = this.customStrategyOp.setCustomStrategy(customStrategies, new Long(customStrategies.get(0).getIdUser()));

            if(customStrategies == null)
                return new GenericResponse<String>(0, "Failure");

            return new GenericResponse<String>(1, "Success");

    }

    @RequestMapping("/getUserCustomStrategyActive")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategyActive(@Valid @RequestBody UserLoggedDTO user){

        this.customStrategyOp = new CustomStrategyOperator(this.customStrategyRep);

        List<CustomStrategyDTO> customStrategyActive = this.customStrategyOp.getUserCustomStrategyActive(user);

        if(customStrategyActive == null )
            return new GenericResponse<String>(0,"Failure");

        return new GenericResponse<List<CustomStrategyDTO>>(1, customStrategyActive);

    }


}
