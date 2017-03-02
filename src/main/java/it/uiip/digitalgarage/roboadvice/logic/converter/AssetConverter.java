package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;

public class AssetConverter implements GenericConverter<AssetEntity, AssetDTO> {

	@Override
	public AssetEntity convertToEntity(AssetDTO dto) {
		AssetEntity entity = new AssetEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public AssetDTO convertToDTO(AssetEntity entity) {
		AssetDTO dto = new AssetDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public List<AssetEntity> convertToEntity(List<AssetDTO> dto) {
		List<AssetEntity> entity = new ArrayList<>();
		for (AssetDTO assetDTO : dto) {
			entity.add(this.convertToEntity(assetDTO));
		}
		return entity;
	}

	@Override
	public List<AssetDTO> convertToDTO(List<AssetEntity> entity) {
		List<AssetDTO> dto = new ArrayList<>();
		for (AssetEntity assetEntity : entity) {
			dto.add(this.convertToDTO(assetEntity));
		}
		return dto;
	}

}
