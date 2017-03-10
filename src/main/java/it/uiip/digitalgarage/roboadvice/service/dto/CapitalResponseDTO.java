package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class CapitalResponseDTO extends CapitalDTO implements Comparable<CapitalResponseDTO> {

	@NotNull
	private String date;

	@Override
	public int compareTo(CapitalResponseDTO o){
		return this.date.compareTo(o.getDate());
	}

}
