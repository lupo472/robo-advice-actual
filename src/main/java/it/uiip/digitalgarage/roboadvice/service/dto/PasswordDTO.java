package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO String password (min 5 chars).
 *
 * @author Cristian Laurini
 */
public @Data class PasswordDTO {

	@NotNull
	@Size(min = 5)
	private String password;

}
