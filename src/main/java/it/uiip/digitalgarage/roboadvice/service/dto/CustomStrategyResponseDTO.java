package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

public @Data class  CustomStrategyResponseDTO extends CustomStrategyDTO implements Comparable<CustomStrategyResponseDTO> {

    @NotNull
    private boolean active;

    @NotNull
    private String date;

	@Override
	public int compareTo(CustomStrategyResponseDTO o) {
		return this.date.compareTo(o.getDate());
	}

}
