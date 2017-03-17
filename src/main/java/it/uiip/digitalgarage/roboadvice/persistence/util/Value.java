package it.uiip.digitalgarage.roboadvice.persistence.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

public @Data @AllArgsConstructor class Value {

	private LocalDate date;

	private BigDecimal value;

}
