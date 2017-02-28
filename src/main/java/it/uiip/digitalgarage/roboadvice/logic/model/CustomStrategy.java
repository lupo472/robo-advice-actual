package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;

public @Data class CustomStrategy {

    private Long id;
    private Long idUser;
    private BigDecimal bonds;
    private BigDecimal stocks;
    private BigDecimal forex;
    private BigDecimal commodities;
    private boolean active;
    private String timestamp;

}
