package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class CapitalDTO extends CapitalRequestDTO implements Comparable<CapitalDTO> {

	@NotNull
	private String date;

	@Override
	public int compareTo(CapitalDTO o){
		return this.date.compareTo(o.getDate());
	}

}
