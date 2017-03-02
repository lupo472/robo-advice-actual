package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;

import java.util.List;

/**
 * Created by Luca on 02/03/2017.
 */
public class CustomStrategyOperator extends GenericOperator{

    public CustomStrategyOperator(CustomStrategyRepository customStrategyRep){
        this.customStrategyRep = customStrategyRep;
    }

    public List<CustomStrategyEntity> getUserCustomStrategies(Long userId){
        return  this.customStrategyRep.findByUserId(userId);

    }

    public CustomStrategyEntity setCustomStrategy(CustomStrategyEntity customStrategy){
        customStrategy = this.customStrategyRep.save(customStrategy);
        return customStrategy;
    }
}
