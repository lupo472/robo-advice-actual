package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class AssetClassStrategyDTO {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private BigDecimal percentage;

}
