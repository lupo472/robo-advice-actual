package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AuthEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AuthDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

@Service
public class UserOperator extends AbstractOperator {
	
	@Autowired
	private AuthOperator authOp;
	
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
			//
			this.authOp.createAuth(userEntity);
//			AuthEntity auth = new AuthEntity();
//			Random random = new SecureRandom();
//			String token = new BigInteger(130, random).toString(32);
//			auth.setToken(token);
//			auth.setUser(userEntity);
//			auth.setDate(LocalDate.now());
//			this.authRep.save(auth);
			//
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
