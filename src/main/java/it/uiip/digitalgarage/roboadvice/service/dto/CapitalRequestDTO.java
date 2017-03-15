package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class CapitalRequestDTO {
	
	@NotNull
	@Min(1)
	private BigDecimal amount;

}
