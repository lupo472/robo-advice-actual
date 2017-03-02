package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;

public class UserConverter implements GenericConverter<UserEntity, UserDTO>{

	@Override
	public UserEntity convertToEntity(UserDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		return entity;
	}

	@Override
	public UserDTO convertToDTO(UserEntity entity) {
		UserDTO dto = new UserDTO();
		dto.setEmail(entity.getEmail());
		dto.setPassword(entity.getPassword());
		return dto;
	}

}
