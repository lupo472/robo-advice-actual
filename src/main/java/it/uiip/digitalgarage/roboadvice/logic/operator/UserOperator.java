package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

public class UserOperator extends GenericOperator {
		
	public UserOperator(UserRepository userRep) {
		this.userRep = userRep;
	}
	
	public UserRequestDTO registerUser(UserRequestDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(userDTO.getEmail());
		String password = userDTO.getPassword();
		password = HashFunction.hashStringSHA256(password);
		userEntity.setPassword(password);
		userEntity.setDate(LocalDate.now());
		userEntity = userRep.save(userEntity);
		userDTO.setPassword(userEntity.getPassword());
		return userDTO;
	}
	
	public UserRequestDTO loginUser(UserRequestDTO userDTO) {
		UserEntity userEntity = this.userRep.findByEmail(userDTO.getEmail());
		String hashedPassword = HashFunction.hashStringSHA256(userDTO.getPassword());
		if(userEntity.getPassword().equals(hashedPassword)) {
			userDTO.setPassword(userEntity.getPassword());
			return userDTO;
		}
		return null;
	}
	
	public boolean isRegistered(String email) {
		return !(this.userRep.findByEmail(email) == null);
	}

}
