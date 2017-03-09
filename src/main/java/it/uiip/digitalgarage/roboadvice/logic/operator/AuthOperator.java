package it.uiip.digitalgarage.roboadvice.logic.operator;

import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.service.dto.AuthDTO;

@Service
public class AuthOperator extends AbstractOperator {

	public boolean authenticate(AuthDTO auth) {
		
		return true;
	}
	
}
