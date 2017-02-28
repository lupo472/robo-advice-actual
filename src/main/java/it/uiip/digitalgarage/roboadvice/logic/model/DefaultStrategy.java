package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;

public @Data class DefaultStrategy {
	
    private Long id;
    private String name;
    private BigDecimal bonds;
    private BigDecimal stoks;
    private BigDecimal forex;
    private BigDecimal commodities;

}
