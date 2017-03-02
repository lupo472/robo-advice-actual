package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;

/**
 * Created by Luca on 02/03/2017.
 */
public class CustomStrategyConverter implements GenericConverter<CustomStrategyEntity,CustomStrategyDTO> {

   @Override
    public CustomStrategyDTO convertToDTO(CustomStrategyEntity customerStrategyEntity){
       CustomStrategyDTO customerStrategyDTO = new CustomStrategyDTO();
       customerStrategyDTO.setId(customerStrategyEntity.getId());
       customerStrategyDTO.setIdUser(customerStrategyEntity.getUser().getId());
       customerStrategyDTO.setIdAssetClass(customerStrategyEntity.getAssetClass().getId());
       customerStrategyDTO.setPercentage(customerStrategyEntity.getPercentage());
       customerStrategyDTO.setActive(customerStrategyEntity.isActive());
       return customerStrategyDTO;
   }

   @Override
    public CustomStrategyEntity convertToEntity(CustomStrategyDTO customStrategyDTO){
        CustomStrategyEntity customStrategyEntity = new CustomStrategyEntity();
        return customStrategyEntity;
   }
}
