package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class PeriodRequestDTO {

	@NotNull
	@Min(0)
	private int period;

}
