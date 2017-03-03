package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

import java.util.List;

/**
 * Created by Luca on 02/03/2017.
 */
public class CustomStrategyOperator extends AbstractOperator{

    public CustomStrategyOperator(CustomStrategyRepository customStrategyRep){
        this.customStrategyRep = customStrategyRep;
    }

    public List<CustomStrategyDTO> getUserCustomStrategies(UserLoggedDTO user){
        List<CustomStrategyEntity> listCustomStrategyEntity = this.customStrategyRep.findByUserId(user.getId());
        List<CustomStrategyDTO> listCustomStrategyDTO = this.customStrategyConv.convertToDTO(listCustomStrategyEntity);
        return listCustomStrategyDTO;

    }

    public CustomStrategyEntity setCustomStrategy(CustomStrategyDTO customStrategy){

        CustomStrategyEntity customStrategyEntityConv = this.customStrategyConv.convertToEntity(customStrategy);
        CustomStrategyEntity customStrategyEntityFinal = this.customStrategyRep.save(customStrategyEntityConv);
        return customStrategyEntityFinal;
    }
}
