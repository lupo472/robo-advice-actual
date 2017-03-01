package it.uiip.digitalgarage.roboadvice.logic;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

public @Data class FinancialData {

    private Long id;
    private Long idAsset;
    private BigDecimal value;
    private LocalDate date;
    
}
