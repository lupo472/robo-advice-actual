package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * DTO that represents an AssetClass with Long id and String name.
 *
 * @author Cristian Laurini
 */
public @Data class AssetClassDTO implements Comparable<AssetClassDTO> {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String name;

	@Override
	public int compareTo(AssetClassDTO o) {
		return this.id.compareTo(o.id);
	}
	
}
