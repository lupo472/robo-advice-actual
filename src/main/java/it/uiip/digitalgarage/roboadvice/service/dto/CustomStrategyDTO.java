package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public @Data class  CustomStrategyDTO {

    @NotNull
    private Long idUser;

    @NotNull
    private Long idAssetClass;

    @NotNull
    private BigDecimal percentage;

    @NotNull
    private boolean active;

    @NotNull
    private String date;

}
