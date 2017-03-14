package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class AssetClassStrategyDTO extends AssetClassDTO {
		
	@NotNull
	private BigDecimal percentage;
	
}
