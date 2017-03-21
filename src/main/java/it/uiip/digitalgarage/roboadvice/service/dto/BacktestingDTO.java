package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public @Data
class BacktestingDTO extends CustomStrategyDTO {

	@NotNull
	@Min(1)
	private int period;

	@NotNull
	@Min(1)
	private BigDecimal capital;

}
