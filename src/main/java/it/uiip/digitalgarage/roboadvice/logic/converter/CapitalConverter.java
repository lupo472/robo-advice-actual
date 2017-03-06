package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;

public class CapitalConverter implements GenericConverter<CapitalEntity, CapitalDTO> {

	@Override
	public CapitalEntity convertToEntity(CapitalDTO dto) {
		CapitalEntity entity = new CapitalEntity();
		entity.setAmount(dto.getAmount());
		UserEntity user = new UserEntity();
		user.setId(dto.getIdUser());
		entity.setUser(user);
		return entity;
	}

	@Override
	public CapitalDTO convertToDTO(CapitalEntity entity) {
		CapitalDTO dto = new CapitalDTO();
		dto.setAmount(entity.getAmount());
		dto.setIdUser(entity.getUser().getId());
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
