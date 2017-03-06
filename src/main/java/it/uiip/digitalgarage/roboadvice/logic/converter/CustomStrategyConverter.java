package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyRequestDTO;

public class CustomStrategyConverter {

	public List<CustomStrategyEntity> convertToEntity(CustomStrategyRequestDTO dto) {
		List<CustomStrategyEntity> entityList = new ArrayList<>();
		for (AssetClassStrategyDTO element : dto.getList()) {
			CustomStrategyEntity entity = new CustomStrategyEntity();
			entity.setId(dto.getIdUser());
			AssetClassEntity assetClass = new AssetClassEntity();
			assetClass.setId(element.getId());
			assetClass.setName(element.getName());
			entity.setAssetClass(assetClass);
			entity.setPercentage(element.getPercentage());
			entityList.add(entity);
		}
		return entityList;
	}
	
	public CustomStrategyDTO convertToDTO(List<CustomStrategyEntity> entityList) {
		CustomStrategyDTO dto = new CustomStrategyDTO();
		dto.setActive(entityList.get(0).isActive());
		dto.setDate(entityList.get(0).getDate().toString());
		dto.setIdUser(entityList.get(0).getUser().getId());
		dto.setList(new ArrayList<>());
		for (CustomStrategyEntity entity : entityList) {
			AssetClassStrategyDTO element = new AssetClassStrategyDTO();
			element.setId(entity.getAssetClass().getId());
			element.setName(entity.getAssetClass().getName());
			element.setPercentage(entity.getPercentage());
			dto.getList().add(element);
		}
		return dto;
	}

}
