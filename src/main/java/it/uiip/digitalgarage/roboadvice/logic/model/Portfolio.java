package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

import lombok.Data;

public @Data class Portfolio {
	
    private Long id;
    private Long idUser;
    private Long idAsset;
    private Long idAssetClass;
    private BigDecimal units;
    private BigDecimal value;
    private String date;

}
