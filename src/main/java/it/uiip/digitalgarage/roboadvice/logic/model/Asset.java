package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;

public @Data class Asset {
	
	private @Getter Long id;
	private Long idAssetClass;
	private String dataSource;
	private BigDecimal percentage;
	private int remarksIndex;
	
}
