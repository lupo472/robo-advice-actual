package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class FinancialDataDTO implements Comparable<FinancialDataDTO> {
	
	@NotNull
	private AssetClassDTO assetClass;
	
	@NotNull
	private BigDecimal value;
	
	@NotNull
	private String date;

	@Override
	public int compareTo(FinancialDataDTO o) {
		return this.date.compareTo(o.date);
	}

}
