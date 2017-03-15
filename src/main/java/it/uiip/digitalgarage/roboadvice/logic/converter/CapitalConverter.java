package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;

public class CapitalConverter {

	public CapitalEntity convertToEntity(CapitalRequestDTO dto) {
		CapitalEntity entity = new CapitalEntity();
		entity.setAmount(dto.getAmount());
		return entity;
	}

	public CapitalDTO convertToDTO(CapitalEntity entity) {
		CapitalDTO dto = new CapitalDTO();
		dto.setAmount(entity.getAmount());
		dto.setDate(entity.getDate().toString());
		return dto;
	}
	
}
