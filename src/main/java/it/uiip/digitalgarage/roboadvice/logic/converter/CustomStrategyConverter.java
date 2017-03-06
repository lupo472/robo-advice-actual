package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;

import java.util.ArrayList;
import java.util.List;

public class CustomStrategyConverter implements GenericConverter<CustomStrategyEntity,CustomStrategyDTO> {

   @Override
    public CustomStrategyDTO convertToDTO(CustomStrategyEntity customerStrategyEntity){
       CustomStrategyDTO customerStrategyDTO = new CustomStrategyDTO();
       customerStrategyDTO.setIdUser(customerStrategyEntity.getUser().getId());
       customerStrategyDTO.setIdAssetClass(customerStrategyEntity.getAssetClass().getId());
       customerStrategyDTO.setPercentage(customerStrategyEntity.getPercentage());
       customerStrategyDTO.setActive(customerStrategyEntity.isActive());
       customerStrategyDTO.setDate(customerStrategyEntity.getDate().toString());
       return customerStrategyDTO;
   }

    @Override
    public List<CustomStrategyEntity> convertToEntity(List<CustomStrategyDTO> dto) {
        List<CustomStrategyEntity> listCustomStrategiesEntities = new ArrayList<CustomStrategyEntity>();

        for(CustomStrategyDTO customDTO : dto){
            listCustomStrategiesEntities.add(this.convertToEntity(customDTO));
        }

        return listCustomStrategiesEntities;
    }

    @Override
    public List<CustomStrategyDTO> convertToDTO(List<CustomStrategyEntity> entity) {
        List<CustomStrategyDTO> listCustomStrategiesDTO = new ArrayList<CustomStrategyDTO>();

        for(CustomStrategyEntity customEntity : entity){
            listCustomStrategiesDTO.add(this.convertToDTO(customEntity));
        }
        return listCustomStrategiesDTO;
    }

    @Override
    public CustomStrategyEntity convertToEntity(CustomStrategyDTO customStrategyDTO){
        CustomStrategyEntity customStrategyEntity = new CustomStrategyEntity();
        UserEntity user = new UserEntity();
        AssetClassEntity assetClass = new AssetClassEntity();

        user.setId(customStrategyDTO.getIdUser());
        assetClass.setId(customStrategyDTO.getIdAssetClass());

        customStrategyEntity.setUser(user);
        customStrategyEntity.setAssetClass(assetClass);
        customStrategyEntity.setPercentage(customStrategyDTO.getPercentage());
        customStrategyEntity.setActive(customStrategyDTO.isActive());

        return customStrategyEntity;
   }

}