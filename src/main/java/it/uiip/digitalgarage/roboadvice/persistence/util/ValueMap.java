package it.uiip.digitalgarage.roboadvice.persistence.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueMap {

	public static Map<LocalDate, BigDecimal> getMap(List<Value> values) {
		Map<LocalDate, BigDecimal> map = new HashMap<>();
		for (Value value : values) {
			map.put(value.getDate(), value.getValue());
		}
		return map;
	}

}
