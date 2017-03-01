package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

public @Data class CustomStrategy {

    private Long id;
    private Long idUser;
    private Long idAssetClass;
    private BigDecimal percentage;
    private boolean active;
    private LocalDate date;

}
