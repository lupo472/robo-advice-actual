package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/*
 * This DTO is used to accept request for different objects.
 * The field 'id' is the identifier of object on the database.
 * The field 'period' contains an amount of days, if it is setted to 0
 * the methods will return the entire data set.
 */
public @Data class DataRequestDTO {

	@NotNull
	@Min(1)
	private Long id;
	
	@Min(0)
	private int period;
	
}
