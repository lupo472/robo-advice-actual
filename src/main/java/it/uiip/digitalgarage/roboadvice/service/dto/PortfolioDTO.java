package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public @Data class PortfolioDTO implements Comparable<PortfolioDTO> {

    @NotNull
    private List<PortfolioElementDTO> list;

    @NotNull
    private String date;

    @Override
    public int compareTo(PortfolioDTO o) {
        return this.date.compareTo(o.getDate());
    }
    
}
