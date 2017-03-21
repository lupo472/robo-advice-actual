package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public @Data
class BacktestingDTO {

	@NotNull
	@Min(1)
	private int period;

	@NotNull
	@Min(1)
	private BigDecimal capital;

	@NotNull
	@Size(min = 1)
	private List<AssetClassStrategyDTO> list;

}
