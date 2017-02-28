package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;

public @Data class Portfolio {
    private Long id;
    private Long idUser;
    private Long id_strategy;
    private Long idAsset;
    private BigDecimal units;
    private BigDecimal value;
    private String date;

}
