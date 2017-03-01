package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;

public @Data class Asset {
	
	private Long id;
	private Long idAssetClass;
	private String name;
	private String dataSource;
	private BigDecimal percentage;
	private int remarksIndex;
	
}
