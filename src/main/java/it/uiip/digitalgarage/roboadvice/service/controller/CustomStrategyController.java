package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class CustomStrategyController extends AbstractController{

    @RequestMapping("/userCustomStrategySet")
    public GenericResponse<?> getUserCustomStrategySet(@RequestBody UserLoggedDTO user){
        try{
            List<CustomStrategyEntity> customStrategies = this.customStrategyOp.getUserCustomStrategies(user.getId());

            if(customStrategies == null) return new GenericResponse<String>(0,"Failure");

            return new GenericResponse<List<CustomStrategyEntity>>(1,customStrategies);

        } catch(Exception e){
            return new GenericResponse<String>(0,"Exception");
        }
    }

    @RequestMapping("/setCustomStrategy")
    public GenericResponse setCustomStrategy(@RequestBody CustomStrategyDTO customStrategy){
        try{
            CustomStrategyEntity createdStrategy = this.customStrategyOp.setCustomStrategy(customStrategy);

            if(customStrategy == null) return new GenericResponse<String>(0, "Failure");

            return new GenericResponse<CustomStrategyEntity>(1, createdStrategy);
        } catch(Exception e){
            return new GenericResponse<String>(0, "Exception");
        }
    }


}
