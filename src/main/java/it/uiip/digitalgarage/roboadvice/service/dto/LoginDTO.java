package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class LoginDTO {

	@NotNull
	private String email;
	
	@NotNull
	private String token;
}
