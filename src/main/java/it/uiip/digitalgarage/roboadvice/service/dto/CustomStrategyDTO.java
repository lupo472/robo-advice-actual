package it.uiip.digitalgarage.roboadvice.service.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

public @Data class CustomStrategyDTO {
	 
	 @NotNull
	 @Size(min = 1)
	 private List<AssetClassStrategyDTO> list;
	
}
