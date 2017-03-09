package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	@Override
	public List<AssetClassEntity> convertToEntity(List<AssetClassDTO> dto) {
		List<AssetClassEntity> entity = new ArrayList<>();
		for (AssetClassDTO assetClassDTO : dto) {
			entity.add(this.convertToEntity(assetClassDTO));
		}
		return entity;
	}

	@Override
	public List<AssetClassDTO> convertToDTO(List<AssetClassEntity> entity) {
		List<AssetClassDTO> dto = new ArrayList<>();
		for (AssetClassEntity assetClassEntity : entity) {
			dto.add(this.convertToDTO(assetClassEntity));
		}
		Collections.sort(dto);
		return dto;
	}

}
