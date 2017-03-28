package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO that represents a single Element of a Portfolio with BigDecimal value, and
 * BigDecimal percentage, Long id, String name inherited from AssetClassStrategyDTO
 *
 * @author Cristian Laurini
 */
public @Data class PortfolioElementDTO extends AssetClassStrategyDTO {

    @NotNull
    private BigDecimal value;

}
