package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Luca on 06/03/2017.
 */
public @Data class PortfolioRequestDTO {

    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    private String date;
}
