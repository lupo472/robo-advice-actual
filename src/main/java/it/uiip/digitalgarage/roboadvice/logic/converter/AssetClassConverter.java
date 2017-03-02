package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

public class AssetClassConverter implements GenericConverter<AssetClassEntity, AssetClassDTO> {

	@Override
	public AssetClassEntity convertToEntity(AssetClassDTO dto) {
		AssetClassEntity entity = new AssetClassEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public AssetClassDTO convertToDTO(AssetClassEntity entity) {
		AssetClassDTO dto = new AssetClassDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

}
