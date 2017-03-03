package it.uiip.digitalgarage.roboadvice.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import lombok.Data;

public @Data class FinancialDataDTO {

	@NotNull
    private Long id;
	
	@NotNull
    private AssetEntity asset;
	
	@NotNull
    private BigDecimal value;
	
	@NotNull
    private LocalDate date;
	
}
