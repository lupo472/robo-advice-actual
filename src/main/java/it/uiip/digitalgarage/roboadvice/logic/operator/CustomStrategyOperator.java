package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomStrategyOperator extends AbstractOperator{

    public CustomStrategyOperator(CustomStrategyRepository customStrategyRep){
        this.customStrategyRep = customStrategyRep;
    }

    public List<CustomStrategyDTO> getUserCustomStrategies(UserLoggedDTO user){

        List<CustomStrategyEntity> listCustomStrategyEntity = this.customStrategyRep.findByUserId(user.getId());
        List<CustomStrategyDTO> listCustomStrategyDTO = this.customStrategyConv.convertToDTO(listCustomStrategyEntity);

        return listCustomStrategyDTO;
    }

    public List<CustomStrategyDTO> setCustomStrategy(List<CustomStrategyDTO> customStrategy, Long userId){

        this.customStrategyRep.setNewActiveForCustomStrategy(userId);

        List<CustomStrategyEntity> customStrategyEntityConv = this.customStrategyConv.convertToEntity(customStrategy);
        List<CustomStrategyEntity> customStrategyEntities = new ArrayList<CustomStrategyEntity>();

        for(CustomStrategyEntity entity : customStrategyEntityConv) {
            entity.setDate(LocalDate.now());
            customStrategyEntities.add(entity);
        }

        List<CustomStrategyDTO> customStrategyDTO = this.customStrategyConv.convertToDTO((List<CustomStrategyEntity>) this.customStrategyRep.save(customStrategyEntities));

        return customStrategyDTO;
    }

    public List<CustomStrategyDTO> getUserCustomStrategyActive(UserLoggedDTO user){

        List<CustomStrategyEntity> customStrategyEntityList = this.customStrategyRep.findByUserIdAndActive(user.getId(), true);
        List<CustomStrategyDTO> customStrategyDTOList = this.customStrategyConv.convertToDTO(customStrategyEntityList);

        return customStrategyDTOList;

    }

}
