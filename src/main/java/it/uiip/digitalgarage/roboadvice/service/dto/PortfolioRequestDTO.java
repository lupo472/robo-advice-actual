package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public @Data class PortfolioRequestDTO {

    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    private String date;
}
