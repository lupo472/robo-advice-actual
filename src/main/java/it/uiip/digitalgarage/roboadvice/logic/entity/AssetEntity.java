package it.uiip.digitalgarage.roboadvice.logic.entity;

import java.math.BigDecimal;

import lombok.Data;

public @Data class AssetEntity {
	
	private Long id;
	private Long idAssetClass;
	private String name;
	private String dataSource;
	private BigDecimal percentage;
	private int remarksIndex;
	
}
