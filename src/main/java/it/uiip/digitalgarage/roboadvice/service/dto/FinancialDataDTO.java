package it.uiip.digitalgarage.roboadvice.service.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * DTO that represents a FinancialData with and AssetClassDTO and a list of FinancialDataElementDTO.
 *
 * @author Cristian Laurini
 */
public @Data class FinancialDataDTO implements Comparable<FinancialDataDTO> {
	
	@NotNull
	private AssetClassDTO assetClass;
	
	@NotNull
	private List<FinancialDataElementDTO> list;

	@Override
	public int compareTo(FinancialDataDTO o) {
		return this.assetClass.compareTo(o.getAssetClass());
	}

}
