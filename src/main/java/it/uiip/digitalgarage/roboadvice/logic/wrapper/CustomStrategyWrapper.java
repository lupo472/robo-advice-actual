package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;

public class CustomStrategyWrapper implements GenericWrapper<CustomStrategyEntity, CustomStrategyDTO>{

	@Override
	public List<CustomStrategyEntity> unwrapToEntity(CustomStrategyDTO dto) {
		List<CustomStrategyEntity> entityList = new ArrayList<>();
		for (AssetClassStrategyDTO element : dto.getList()) {
			CustomStrategyEntity entity = new CustomStrategyEntity();
			entity.setId(dto.getIdUser());
			AssetClassEntity assetClass = new AssetClassEntity();
			assetClass.setId(element.getAssetClass().getId());
			assetClass.setName(element.getAssetClass().getName());
			entity.setAssetClass(assetClass);
			entity.setPercentage(element.getPercentage());
			entityList.add(entity);
		}
		return entityList;
	}
	
	@Override 
	public CustomStrategyResponseDTO wrapToDTO(List<CustomStrategyEntity> entityList) {
		CustomStrategyResponseDTO dto = new CustomStrategyResponseDTO();
		dto.setActive(entityList.get(0).isActive());
		dto.setDate(entityList.get(0).getDate().toString());
		dto.setIdUser(entityList.get(0).getUser().getId());
		dto.setList(new ArrayList<>());
		for (CustomStrategyEntity entity : entityList) {
			AssetClassStrategyDTO element = new AssetClassStrategyDTO();
			AssetClassDTO assetClass = new AssetClassDTO();
			assetClass.setId(entity.getAssetClass().getId());
			assetClass.setName(entity.getAssetClass().getName());
			element.setAssetClass(assetClass);
			element.setPercentage(entity.getPercentage());
			dto.getList().add(element);
		}
		return dto;
	}

}
