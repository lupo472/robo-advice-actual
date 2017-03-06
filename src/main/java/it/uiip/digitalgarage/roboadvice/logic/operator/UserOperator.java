package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

public class UserOperator extends AbstractOperator {
		
	public UserOperator(UserRepository userRep) {
		this.userRep = userRep;
	}
	
	public UserLoggedDTO registerUser(UserDTO userDTO) {
		UserEntity userEntity = this.userConv.convertToEntity(userDTO);
		String password = HashFunction.hashStringSHA256(userDTO.getPassword());
		userEntity.setPassword(password);
		userEntity.setDate(LocalDate.now());
		userEntity = userRep.save(userEntity);
		UserLoggedDTO userLoggedDTO = (UserLoggedDTO) this.userConv.convertToDTO(userEntity);
		return userLoggedDTO;
	}
	
	public UserLoggedDTO loginUser(UserDTO userDTO) {
		UserEntity userEntity = this.userRep.findByEmail(userDTO.getEmail());
		String hashedPassword = HashFunction.hashStringSHA256(userDTO.getPassword());
		if(userEntity.getPassword().equals(hashedPassword)) {
			UserLoggedDTO userLoggedDTO = (UserLoggedDTO) this.userConv.convertToDTO(userEntity);
			return userLoggedDTO;
		}
		return null;
	}
	
	public boolean isRegistered(String email) {
		return !(this.userRep.findByEmail(email) == null);
	}

}
