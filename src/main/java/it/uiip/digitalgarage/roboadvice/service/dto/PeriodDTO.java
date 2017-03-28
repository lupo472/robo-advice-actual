package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * DTO with int period (min 0)
 *
 * @author Cristian Laurini
 */
public @Data class PeriodDTO {

	@NotNull
	@Min(0)
	private int period;

}
