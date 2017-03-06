package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Luca on 06/03/2017.
 */
public @Data class PortfolioElementsDTO {

    @NotNull
    private AssetDTO asset;

    @NotNull
    private BigDecimal units;

    @NotNull
    private BigDecimal value;
}
