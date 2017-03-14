package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public @Data class PortfolioElementDTO extends AssetClassStrategyDTO {

    @NotNull
    private BigDecimal value;
}
