package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

@Service
public class UserOperator extends AbstractOperator {
	
	public UserRegisteredDTO registerUser(UserDTO userDTO) {
		UserEntity userEntity = this.userConv.convertToEntity(userDTO);
		String password = HashFunction.hashStringSHA256(userDTO.getPassword());
		userEntity.setPassword(password);
		userEntity.setDate(LocalDate.now());
		userEntity = userRep.save(userEntity);
		UserRegisteredDTO userLoggedDTO = (UserRegisteredDTO) this.userConv.convertToDTO(userEntity);
		return userLoggedDTO;
	}
	
	public UserRegisteredDTO loginUser(UserDTO userDTO) {
		UserEntity userEntity = this.userRep.findByEmail(userDTO.getEmail());
		String hashedPassword = HashFunction.hashStringSHA256(userDTO.getPassword());
		if(userEntity.getPassword().equals(hashedPassword)) {
			UserRegisteredDTO userLoggedDTO = (UserRegisteredDTO) this.userConv.convertToDTO(userEntity);
			return userLoggedDTO;
		}
		return null;
	}
	
	public boolean isRegistered(String email) {
		return !(this.userRep.findByEmail(email) == null);
	}
	
	public List<UserRegisteredDTO> getAllUsers() {
		List<UserEntity> entities = this.userRep.findAll();
		List<UserRegisteredDTO> users = new ArrayList<>();
		for(UserEntity entity : entities) {
			UserRegisteredDTO user = (UserRegisteredDTO) this.userConv.convertToDTO(entity);
			users.add(user);
		}
		return users;
	} 

}
