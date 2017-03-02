package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
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
    	List<CustomStrategyEntity> customStrategies = customStrategyRep.findByUserId(user.getId());
        return new GenericResponse<List<CustomStrategyEntity>>(1,customStrategies);
    }
    	
    //TODO implement this method
//    @RequestMapping("/addCustomStrategy")
//    public GenericResponse addCustomStrategy(@RequestBody CustomStrategyEntity customStrategy){
//        try{
//            return null;
//        } catch(Exception e){
//            return new GenericResponse<String>(0,"Exception");
//        }
//    }


}
