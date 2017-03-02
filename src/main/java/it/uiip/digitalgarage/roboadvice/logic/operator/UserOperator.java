package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

public class UserOperator extends GenericOperator {
		
	public UserOperator(UserRepository userRep) {
		this.userRep = userRep;
	}
	
	public UserLoggedDTO registerUser(UserDTO userRequestDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(userRequestDTO.getEmail());
		String password = userRequestDTO.getPassword();
		password = HashFunction.hashStringSHA256(password);
		userEntity.setPassword(password);
		userEntity.setDate(LocalDate.now());
		userEntity = userRep.save(userEntity);
		UserLoggedDTO userLoggedDTO = new UserLoggedDTO();
		userLoggedDTO.setEmail(userEntity.getEmail());
		userLoggedDTO.setPassword(userEntity.getPassword());
		userLoggedDTO.setId(userEntity.getId());
		return userLoggedDTO;
	}
	
	public UserLoggedDTO loginUser(UserDTO userRequestDTO) {
		UserEntity userEntity = this.userRep.findByEmail(userRequestDTO.getEmail());
		String hashedPassword = HashFunction.hashStringSHA256(userRequestDTO.getPassword());
		if(userEntity.getPassword().equals(hashedPassword)) {
			UserLoggedDTO userLoggedDTO = new UserLoggedDTO();
			userLoggedDTO.setEmail(userEntity.getEmail());
			userLoggedDTO.setPassword(userEntity.getPassword());
			userLoggedDTO.setId(userEntity.getId());
			return userLoggedDTO;
		}
		return null;
	}
	
	public boolean isRegistered(String email) {
		return !(this.userRep.findByEmail(email) == null);
	}

}
