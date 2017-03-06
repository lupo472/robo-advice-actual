package it.uiip.digitalgarage.roboadvice.service.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class DefaultStrategyDTO {
	
	@NotNull
	private String name;
	
	@NotNull
	private List<AssetClassStrategyDTO> list;

}
