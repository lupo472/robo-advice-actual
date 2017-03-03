package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;

import java.util.List;

/**
 * Created by Luca on 02/03/2017.
 */
public class CustomStrategyConverter implements GenericConverter<CustomStrategyEntity,CustomStrategyDTO> {

   @Override
    public CustomStrategyDTO convertToDTO(CustomStrategyEntity customerStrategyEntity){
       CustomStrategyDTO customerStrategyDTO = new CustomStrategyDTO();
       customerStrategyDTO.setIdUser(customerStrategyEntity.getUser().getId());
       customerStrategyDTO.setIdAssetClass(customerStrategyEntity.getAssetClass().getId());
       customerStrategyDTO.setPercentage(customerStrategyEntity.getPercentage());
       customerStrategyDTO.setActive(customerStrategyEntity.isActive());
       return customerStrategyDTO;
   }

    @Override
    public List<CustomStrategyEntity> convertToEntity(List<CustomStrategyDTO> dto) {
        return null;
    }

    @Override
    public List<CustomStrategyDTO> convertToDTO(List<CustomStrategyEntity> entity) {
        return null;
    }

    @Override
    public CustomStrategyEntity convertToEntity(CustomStrategyDTO customStrategyDTO){
        CustomStrategyEntity customStrategyEntity = new CustomStrategyEntity();
        return customStrategyEntity;
   }


}
