package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;

@Component
public class CustomStrategyWrapper {

	public List<CustomStrategyEntity> unwrapToEntity(CustomStrategyDTO dto) {
		List<CustomStrategyEntity> entityList = new ArrayList<>();
		for (AssetClassStrategyDTO element : dto.getList()) {
			CustomStrategyEntity entity = new CustomStrategyEntity();
			AssetClassEntity assetClass = new AssetClassEntity();
			assetClass.setId(element.getId());
			assetClass.setName(element.getName());
			entity.setAssetClass(assetClass);
			entity.setPercentage(element.getPercentage());
			entityList.add(entity);
		}
		return entityList;
	}
	
	public CustomStrategyResponseDTO wrapToDTO(List<CustomStrategyEntity> entityList) {
		CustomStrategyResponseDTO dto = new CustomStrategyResponseDTO();
		dto.setActive(entityList.get(0).isActive());
		dto.setDate(entityList.get(0).getDate().toString());
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
