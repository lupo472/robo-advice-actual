package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class CapitalResponseDTO extends CapitalDTO {

	@NotNull
	private String date;
	
}
