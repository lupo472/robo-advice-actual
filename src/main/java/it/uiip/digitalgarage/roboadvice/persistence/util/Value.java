package it.uiip.digitalgarage.roboadvice.persistence.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class represents an Object used in PortfolioRepository with LocalDate date and BigDecimal value.
 *
 * @author Cristian Laurini
 */
public @Data @AllArgsConstructor class Value {

	private LocalDate date;

	private BigDecimal value;

}
