package it.uiip.digitalgarage.roboadvice.logic.converter;

import org.springframework.stereotype.Component;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;

/**
 * This class offers a method to convert a UserDTO in a UserEntity.
 *
 * @author Cristian Laurini
 */
@Component
public class UserConverter {

	/**
	 * This method converts a single UserDTO in a UserEntity.
	 *
	 * @param dto	UserDTO is the dto to convert.
	 * @return		UserEntity is the result of the conversion.
	 */
	public UserEntity convertToEntity(UserDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		return entity;
	}

}
