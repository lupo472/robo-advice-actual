package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.Min;

import lombok.Data;

public @Data class PeriodRequestDTO {
	
	@Min(0)
	private int period;

}
