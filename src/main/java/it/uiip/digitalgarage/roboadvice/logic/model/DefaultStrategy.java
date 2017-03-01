package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;

public @Data class DefaultStrategy {
	
    private Long id;
    private String name;
    private Long idAssetClass;
    private BigDecimal percentage;

}
