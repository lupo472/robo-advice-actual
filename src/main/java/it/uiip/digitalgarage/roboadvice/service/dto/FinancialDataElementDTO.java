package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class FinancialDataElementDTO implements Comparable<FinancialDataElementDTO>  {

	@NotNull
	private BigDecimal value;
	
	@NotNull
	private String date;
	
	@Override
	public int compareTo(FinancialDataElementDTO o) {
		return this.date.compareTo(o.getDate());
	}
	
}
