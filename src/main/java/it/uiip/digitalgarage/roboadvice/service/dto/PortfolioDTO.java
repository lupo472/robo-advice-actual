package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public @Data class PortfolioDTO {

    @NotNull
    private Long idUser;

    @NotNull
    private List<PortfolioElementDTO> list;

    @NotNull
    private String date;
    
}
