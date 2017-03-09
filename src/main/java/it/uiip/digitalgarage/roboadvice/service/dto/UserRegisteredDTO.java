package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class UserLoggedDTO extends UserDTO {
	
	@NotNull
	private Long id;

}
