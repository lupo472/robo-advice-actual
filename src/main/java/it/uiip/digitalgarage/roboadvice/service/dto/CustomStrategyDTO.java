package it.uiip.digitalgarage.roboadvice.service.dto;

/**
 * Created by Luca on 02/03/2017.
 */

import lombok.Data;

import java.math.BigDecimal;

public @Data class  CustomStrategyDTO {

    private Long idUser;
    private Long idAssetClass;
    private BigDecimal percentage;
    private boolean active;

}
