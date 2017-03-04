package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Luca on 03/03/2017.
 */
public @Data class PortfolioDTO {

    @NotNull
    private Long idUser;

    @NotNull
    private Long idAsset;

    @NotNull
    private Long idAssetClass;

    @NotNull
    private BigDecimal units;

    @NotNull
    private BigDecimal value;

    private String date;
}
