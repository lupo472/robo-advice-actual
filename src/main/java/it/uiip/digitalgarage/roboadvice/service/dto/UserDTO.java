package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.Data;

/**
 * DTO that represents an User with String email and String password (min 5 chars).
 *
 * @author Cristian Laurini
 */
public @Data class UserDTO {

	@NotNull
	@Email
	protected String email;
	
	@NotNull
	@Size(min = 5)
	protected String password;
	
}
