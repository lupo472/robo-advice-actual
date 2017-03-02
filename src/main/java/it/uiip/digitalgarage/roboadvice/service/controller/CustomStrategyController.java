package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Luca on 02/03/2017.
 */


@CrossOrigin("*")
@RestController
public class CustomStrategyController extends GenericController{

    @RequestMapping("/userCustomStrategySet")
    public GenericResponse getUserCustomStrategySet(@RequestBody UserEntity user){
        try{
            ArrayList<CustomStrategyEntity> customStrategies = (ArrayList<CustomStrategyEntity>) customStrategyRep.findByUserEntity(user);
            return new GenericResponse(1,customStrategies);

        } catch(Exception e){
            return new GenericResponse(0,"Exception");
        }
    }

    @RequestMapping("/addCustomStrategy")
    public GenericResponse addCustomStrategy(@RequestBody CustomStrategyEntity customStrategy){
        try{
            return null;
        } catch(Exception e){
            return new GenericResponse(0,"Exception");
        }
    }


}
