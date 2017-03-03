package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class DataForAssetRequestDTO {

	@NotNull
	@Min(1)
	private Long idAsset;
	
	@Min(0)
	private int period;
	
}
