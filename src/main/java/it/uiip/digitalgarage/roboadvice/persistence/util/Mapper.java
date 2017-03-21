package it.uiip.digitalgarage.roboadvice.persistence.util;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapper {

	public static Map<LocalDate, BigDecimal> getMapValues(List<Value> values) {
		Map<LocalDate, BigDecimal> map = new HashMap<>();
		for (Value value : values) {
			map.put(value.getDate(), value.getValue());
		}
		return map;
	}

	public static Map<Long, FinancialDataEntity> getMapFinancialData(List<FinancialDataEntity> data) {
		Map<Long, FinancialDataEntity> map = new HashMap<>();
		for(FinancialDataEntity financialData : data) {
			map.put(financialData.getAsset().getId(), financialData);
		}
		return map;
	}

	public static Map<Long, List<AssetEntity>> getMapAssets(List<AssetEntity> assets) {
		Map<Long, List<AssetEntity>> map = new HashMap<>();
		for(AssetEntity asset : assets) {
			Long idClass = asset.getAssetClass().getId();
			if(map.get(idClass) == null) {
				map.put(idClass, new ArrayList<>());
			}
			map.get(idClass).add(asset);
		}
		return map;
	}

	public static Map<Long, PortfolioEntity> getMapPortfolio(List<PortfolioEntity> portfolioList) {
		Map<Long, PortfolioEntity> map = new HashMap<>();
		for(PortfolioEntity portfolio : portfolioList) {
			Long id = portfolio.getAsset().getId();
			map.put(id, portfolio);
		}
		return map;
	}

}
