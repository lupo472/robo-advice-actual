package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AuthEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AuthDTO;

public class AuthConverter implements GenericConverter<AuthEntity, AuthDTO> {

	@Override
	public AuthEntity convertToEntity(AuthDTO dto) {
		AuthEntity entity = new AuthEntity();
		UserEntity user = new UserEntity();
		user.setId(dto.getIdUser());
		entity.setUser(user);
		entity.setToken(dto.getToken());
		return entity;
	}

	@Override
	public AuthDTO convertToDTO(AuthEntity entity) {
		AuthDTO dto = new AuthDTO();
		dto.setIdUser(entity.getUser().getId());
		dto.setToken(entity.getToken());
		return dto;
	}

	@Override
	public List<AuthEntity> convertToEntity(List<AuthDTO> dto) {
		List<AuthEntity> list = new ArrayList<>();
		for (AuthDTO authDTO : dto) {
			list.add(this.convertToEntity(authDTO));
		}
		return list;
	}

	@Override
	public List<AuthDTO> convertToDTO(List<AuthEntity> entity) {
		List<AuthDTO> list = new ArrayList<>();
		for (AuthEntity authEntity : entity) {
			list.add(this.convertToDTO(authEntity));
		}
		return list;
	}

}
