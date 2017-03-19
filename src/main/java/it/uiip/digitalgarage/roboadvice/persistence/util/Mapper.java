package it.uiip.digitalgarage.roboadvice.persistence.util;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapper {

	public static Map<LocalDate, BigDecimal> getMap(List<Value> values) {
		Map<LocalDate, BigDecimal> map = new HashMap<>();
		for (Value value : values) {
			map.put(value.getDate(), value.getValue());
		}
		return map;
	}

	public static Map<AssetEntity, FinancialDataEntity> getMapFinancialData(List<FinancialDataEntity> data) {
		Map<AssetEntity, FinancialDataEntity> map = new HashMap<>();
		for(FinancialDataEntity financialData : data) {
			map.put(financialData.getAsset(), financialData);
		}
		return map;
	}

}
