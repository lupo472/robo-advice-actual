package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class UserRegisteredDTO extends UserDTO {
	
	@NotNull
	private Long id;

}
