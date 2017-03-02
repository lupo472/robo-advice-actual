package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<UserEntity> convertToEntity(List<UserDTO> dto) {
		List<UserEntity> entity = new ArrayList<>();
		for (UserDTO userDTO : dto) {
			entity.add(this.convertToEntity(userDTO));
		}
		return entity;
	}

	@Override
	public List<UserDTO> convertToDTO(List<UserEntity> entity) {
		List<UserDTO> dto = new ArrayList<>();
		for (UserEntity userEntity : entity) {
			dto.add(this.convertToDTO(userEntity));
		}
		return dto;
	}

}
