package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class AuthDTO {

	@NotNull
	private Long id;
	
	@NotNull
	private String token;
	
}
