package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;

import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AuthEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AuthDTO;

@Service
public class AuthOperator extends AbstractOperator {

	public boolean authenticate(AuthDTO auth) {
		
		return true;
	}
	
	public AuthDTO createAuth(UserEntity user) {
		AuthEntity auth = new AuthEntity();
		Random random = new SecureRandom();
		String token = new BigInteger(130, random).toString(32);
		auth.setToken(token);
		auth.setUser(user);
		auth.setDate(LocalDate.now());
		this.checkAuth(auth);
		AuthEntity entity = this.authRep.save(auth);
		return this.authConv.convertToDTO(entity);
	}
	
	private void checkAuth(AuthEntity auth) {
		AuthEntity savedAuth = this.authRep.findByUserId(auth.getUser().getId());
		if(savedAuth != null) {
			this.authRep.delete(savedAuth.getId());
		}
	}
	
}
