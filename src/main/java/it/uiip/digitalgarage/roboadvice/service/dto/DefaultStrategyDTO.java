package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.List;

public @Data class DefaultStrategyDTO implements Comparable<DefaultStrategyDTO> {
	
	@NotNull
	private String name;
	
	@NotNull
	@Size(min = 1)
	private List<AssetClassStrategyDTO> list;
	
	@NotNull
	private int risk;

	@Override
	public int compareTo(DefaultStrategyDTO o) {
		return this.risk - o.risk;	
	}
	
}
