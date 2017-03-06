package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

public @Data class  CustomStrategyDTO extends CustomStrategyRequestDTO {

    @NotNull
    private boolean active;

    @NotNull
    private String date;

}
