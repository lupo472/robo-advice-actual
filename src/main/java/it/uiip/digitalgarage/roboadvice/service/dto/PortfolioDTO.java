package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO that represents a Portfolio with String date and a List of PortfolioElementDTO.
 *
 * @author Cristian Laurini
 */
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
