package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class AssetClassStrategyDTO implements Comparable<AssetClassStrategyDTO> {
	
	@NotNull
	private AssetClassDTO assetClass;
	
	@NotNull
	private BigDecimal percentage;

	@Override
	public int compareTo(AssetClassStrategyDTO o) {
		return this.assetClass.getId().compareTo(o.getAssetClass().getId());
	}
}
