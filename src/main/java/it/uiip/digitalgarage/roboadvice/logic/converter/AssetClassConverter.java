package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

@Component
public class AssetClassConverter {

	public AssetClassDTO convertToDTO(AssetClassEntity entity) {
		AssetClassDTO dto = new AssetClassDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
	
	public List<AssetClassDTO> convertToDTO(List<AssetClassEntity> entity) {
		List<AssetClassDTO> dto = new ArrayList<>();
		for (AssetClassEntity assetClassEntity : entity) {
			dto.add(this.convertToDTO(assetClassEntity));
		}
		Collections.sort(dto);
		return dto;
	}

}
