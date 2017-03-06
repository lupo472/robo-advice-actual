package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public @Data class PortfolioDTO {

    @NotNull
    private Long idUser;

    @NotNull
    private List<PortfolioElementsDTO> elements;

    @NotNull
    private String date;
    
}
