package it.uiip.digitalgarage.roboadvice.service.dto;

/**
 * Created by Luca on 02/03/2017.
 */

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public @Data class  CustomStrategyDTO {

    @NotNull
    private Long idAssetClass;

    @NotNull
    private BigDecimal percentage;

    @NotNull
    private boolean active;

    @NotNull
    private String date;

}
