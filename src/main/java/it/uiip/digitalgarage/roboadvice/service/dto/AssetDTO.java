package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import lombok.Data;

public @Data class AssetDTO {

	@NotNull
	private Long id;
	
	@NotNull
	private AssetClassEntity assetClass;
	
	@NotNull
	private String name;
	
	@NotNull
	private String dataSource;
	
	@NotNull
	private BigDecimal percentage;
	
}
