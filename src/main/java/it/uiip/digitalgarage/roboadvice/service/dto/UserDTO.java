package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.Data;

public @Data class UserDTO {
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min = 5)
	private String password;
	
}
