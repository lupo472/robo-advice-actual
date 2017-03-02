package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

public class UserLoggedConverter implements GenericConverter<UserEntity, UserLoggedDTO> {

	@Override
	public UserEntity convertToEntity(UserLoggedDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.setId(dto.getId());
		return entity;
	}

	@Override
	public UserLoggedDTO convertToDTO(UserEntity entity) {
		UserLoggedDTO dto = new UserLoggedDTO();
		dto.setEmail(entity.getEmail());
		dto.setPassword(entity.getPassword());
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public List<UserEntity> convertToEntity(List<UserLoggedDTO> dto) {
		List<UserEntity> entity = new ArrayList<>();
		for (UserLoggedDTO userLoggedDTO : dto) {
			entity.add(this.convertToEntity(userLoggedDTO));
		}
		return entity;
	}

	@Override
	public List<UserLoggedDTO> convertToDTO(List<UserEntity> entity) {
		List<UserLoggedDTO> dto = new ArrayList<>();
		for (UserEntity userEntity : entity) {
			dto.add(this.convertToDTO(userEntity));
		}
		return dto;
	}

}
