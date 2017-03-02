package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

public class UserOperator extends GenericOperator {
		
	public UserOperator(UserRepository userRep) {
		this.userRep = userRep;
	}
	
	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(userRequestDTO.getEmail());
		String password = userRequestDTO.getPassword();
		password = HashFunction.hashStringSHA256(password);
		userEntity.setPassword(password);
		userEntity.setDate(LocalDate.now());
		userEntity = userRep.save(userEntity);
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setEmail(userEntity.getEmail());
		userResponseDTO.setPassword(userEntity.getPassword());
		userResponseDTO.setId(userEntity.getId());
		return userResponseDTO;
	}
	
	public UserResponseDTO loginUser(UserRequestDTO userRequestDTO) {
		UserEntity userEntity = this.userRep.findByEmail(userRequestDTO.getEmail());
		String hashedPassword = HashFunction.hashStringSHA256(userRequestDTO.getPassword());
		if(userEntity.getPassword().equals(hashedPassword)) {
			UserResponseDTO userResponseDTO = new UserResponseDTO();
			userResponseDTO.setEmail(userEntity.getEmail());
			userResponseDTO.setPassword(userEntity.getPassword());
			userResponseDTO.setId(userEntity.getId());
			return userResponseDTO;
		}
		return null;
	}
	
	public boolean isRegistered(String email) {
		return !(this.userRep.findByEmail(email) == null);
	}

}
