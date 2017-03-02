package it.uiip.digitalgarage.roboadvice.service.dto;

import java.util.List;

import lombok.Data;

public @Data class DefaultStrategyDTO {
	
	private String name;
	private List<AssetClassStrategyDTO> list;

}
