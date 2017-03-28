package it.uiip.digitalgarage.roboadvice.logic.converter;

import org.springframework.stereotype.Component;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;

/**
 * This class offers methods to convert a CapitalEntity in a CapitalDTO and vice-versa.
 *
 * @author Cristian Laurini
 */
@Component
public class CapitalConverter {

	/**
	 * This method converts a single CapitalRequestDTO in a CapitalEntity.
	 *
	 * @param dto		CapitalRequestDTO is the dto to convert.
	 * @return			CapitalEntity is the result of the conversion.
	 */
	public CapitalEntity convertToEntity(CapitalRequestDTO dto) {
		CapitalEntity entity = new CapitalEntity();
		entity.setAmount(dto.getAmount());
		return entity;
	}

	/**
	 * This method converts a single CapitalEntity in a CapitalDTO.
	 *
	 * @param entity	CapitalEntity is the entity to convert.
	 * @return			CapitalDTO is the result of the conversion.
	 */
	public CapitalDTO convertToDTO(CapitalEntity entity) {
		CapitalDTO dto = new CapitalDTO();
		dto.setAmount(entity.getAmount());
		dto.setDate(entity.getDate().toString());
		return dto;
	}
	
}
