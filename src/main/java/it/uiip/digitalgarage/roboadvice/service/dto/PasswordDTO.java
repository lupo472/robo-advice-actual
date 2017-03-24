package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public @Data class PasswordDTO {

	@NotNull
	@Size(min = 5)
	private String password;

}
