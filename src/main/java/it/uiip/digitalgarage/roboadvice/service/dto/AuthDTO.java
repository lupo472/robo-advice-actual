package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class AuthDTO {

	@NotNull
	private Long idUser;
	
	@NotNull
	private String token;
	
}
