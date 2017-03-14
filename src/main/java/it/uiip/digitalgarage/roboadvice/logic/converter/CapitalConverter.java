package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalResponseDTO;

public class CapitalConverter implements GenericConverter<CapitalEntity, CapitalDTO> {

	@Override
	public CapitalEntity convertToEntity(CapitalDTO dto) {
		CapitalEntity entity = new CapitalEntity();
		entity.setAmount(dto.getAmount());
		return entity;
	}

	@Override
	public CapitalResponseDTO convertToDTO(CapitalEntity entity) {
		CapitalResponseDTO dto = new CapitalResponseDTO();
		dto.setAmount(entity.getAmount());
		dto.setDate(entity.getDate().toString());
		return dto;
	}

	@Override
	public List<CapitalEntity> convertToEntity(List<CapitalDTO> dto) {
		List<CapitalEntity> entityList = new ArrayList<>();
		for (CapitalDTO singleDTO : dto) {
			CapitalEntity entity = this.convertToEntity(singleDTO);
			entityList.add(entity);
		}
		return entityList;
	}

	@Override
	public List<CapitalDTO> convertToDTO(List<CapitalEntity> entity) {
		List<CapitalDTO> dtoList = new ArrayList<>();
		for (CapitalEntity singleEntity : entity) {
			CapitalDTO dto = this.convertToDTO(singleEntity);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
}
