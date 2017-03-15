package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

@Service
public class UserOperator extends AbstractOperator {
	
	public boolean registerUser(UserDTO userDTO) {
		if(this.isRegistered(userDTO.getEmail())) {
			return false;
		}
		UserEntity userEntity = this.userConv.convertToEntity(userDTO);
		String password = HashFunction.hashStringSHA256(userDTO.getPassword());
		userEntity.setPassword(password);
		userEntity.setDate(LocalDate.now());
		userRep.save(userEntity);
		return true;
	}

	public LoginDTO loginUser(UserDTO userDTO) {
		UserEntity userEntity = this.userRep.findByEmail(userDTO.getEmail());
		String hashedPassword = HashFunction.hashStringSHA256(userDTO.getPassword());
		if(userEntity.getPassword().equals(hashedPassword)) {
			LoginDTO login = new LoginDTO();
			login.setEmail(userEntity.getEmail());
			String secretKey = "inglouriousBasterds";
			String token = Jwts.builder().setSubject(userDTO.getEmail()).claim("role", "USER").setIssuedAt(new Date())
						   .signWith(SignatureAlgorithm.HS256, secretKey).compact();
			login.setToken(token);
			return login;
		}	
		return null;
	}
	
	public boolean isRegistered(String email) {
		return !(this.userRep.findByEmail(email) == null);
	}
	
	public List<UserEntity> getAllUsers() {
		List<UserEntity> entities = this.userRep.findAll();
		return entities;
	} 

}
