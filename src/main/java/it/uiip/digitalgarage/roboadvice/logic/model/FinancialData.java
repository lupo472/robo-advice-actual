package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;

public @Data class FinancialData {

    private Long id;
    private Long idAsset;
    private BigDecimal value;
    private String date;
    
}
