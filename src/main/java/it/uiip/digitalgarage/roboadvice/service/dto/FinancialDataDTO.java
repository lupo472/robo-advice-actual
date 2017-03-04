package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class FinancialDataDTO implements Comparable<FinancialDataClassDTO> {
	
	@NotNull
    private AssetDTO asset;
	
	@NotNull
    private BigDecimal value;
	
	@NotNull
    private String date;

	@Override
	public int compareTo(FinancialDataClassDTO o) {
		return this.date.compareTo(o.getDate());
	}
	
}
