package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * DTO with String email and String token.
 *
 * @author Cristian Laurini
 */
public @Data class LoginDTO {

	@NotNull
	private String email;
	
	@NotNull
	private String token;
}
