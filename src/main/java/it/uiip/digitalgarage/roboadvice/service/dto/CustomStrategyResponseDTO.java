package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

public @Data class  CustomStrategyResponseDTO extends CustomStrategyDTO {

    @NotNull
    private boolean active;

    @NotNull
    private String date;

}
