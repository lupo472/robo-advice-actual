package it.uiip.digitalgarage.roboadvice.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public @Data class BacktestingDTO extends CustomStrategyDTO {

	@NotNull
	@Min(1)
	private int period;

	@NotNull
	@Min(1)
	private BigDecimal capital;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		BacktestingDTO that = (BacktestingDTO) o;

		if (period != that.period) {
			return false;
		}
		if(!capital.equals(that.capital)) {
			return false;
		}
		if(this.getList().size() != that.getList().size()) {
			return false;
		}
		return true;
	}

}
