package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.List;

/**
 * DTO that represents a DefaultStrategy with String name, int risk and a List
 * of AssetClassStrategyDTO (min 1 element).
 *
 * @author Cristian Laurini
 */
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
