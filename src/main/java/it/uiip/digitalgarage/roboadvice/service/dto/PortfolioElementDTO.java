package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public @Data class PortfolioElementDTO {

    @NotNull
    private AssetDTO asset;

    @NotNull
    private BigDecimal units;

    @NotNull
    private BigDecimal value;
}
