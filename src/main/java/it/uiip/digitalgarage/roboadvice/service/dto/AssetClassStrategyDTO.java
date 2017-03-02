package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import lombok.Data;

public @Data class AssetClassStrategyDTO {
	
	private Long id;
	private String name;
	private BigDecimal percentage;

}
