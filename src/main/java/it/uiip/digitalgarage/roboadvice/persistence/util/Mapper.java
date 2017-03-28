package it.uiip.digitalgarage.roboadvice.persistence.util;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains methods to convert a List of Objects in a Map.
 *
 * @author Cristian Laurini
 */
public class Mapper {

	/**
	 * This method converts a List of Value in a Map with the date as key and the value as value.
	 *
	 * @param values	List of Values.
	 * @return			Map with date as key and value as value.
	 */
	public static Map<LocalDate, BigDecimal> getMapValues(List<Value> values) {
		Map<LocalDate, BigDecimal> map = new HashMap<>();
		for (Value value : values) {
			map.put(value.getDate(), value.getValue());
		}
		return map;
	}

	/**
	 * This method converts a List of FinancialData in a Map with the id of the asset as key and the
	 * FinancialDataEntity as value.
	 *
	 * @param data	List of FinancialDataEntities.
	 * @return		Map with asset id as key and financialDataEntity as value.
	 */
	public static Map<Long, FinancialDataEntity> getMapFinancialData(List<FinancialDataEntity> data) {
		Map<Long, FinancialDataEntity> map = new HashMap<>();
		for(FinancialDataEntity financialData : data) {
			map.put(financialData.getAsset().getId(), financialData);
		}
		return map;
	}

	/**
	 * This method converts a List of AssetEntities in a Map with the id of the asset class as key and the
	 * List of AssetEntites for that class as value.
	 *
	 * @param assets	List of AssetEntities.
	 * @return			Map with asset class id as key and List of AssetEntities as value.
	 */
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

	/**
	 * This method converts a List of PortfolioEntities in a Map with the id of the asset as key and the
	 * PortfolioEntity as value.
	 *
	 * @param portfolioList		List of PortfolioEntities.
	 * @return					Map with asset id as key and PortfolioEntity as value.
	 */
	public static Map<Long, PortfolioEntity> getMapPortfolio(List<PortfolioEntity> portfolioList) {
		Map<Long, PortfolioEntity> map = new HashMap<>();
		for(PortfolioEntity portfolio : portfolioList) {
			Long id = portfolio.getAsset().getId();
			map.put(id, portfolio);
		}
		return map;
	}

	/**
	 * This method converts a List of CustomStrategyEntities in a Map with the id of the asset class as key and
	 * the CustomStrategyEntity as value.
	 *
	 * @param customStrategyList	List of CustomStrategyEntities.
	 * @return						Map with asset id as key and CustomStrategyEntity as value.
	 */
	public static Map<Long, CustomStrategyEntity> getMapCustomStrategy(List<CustomStrategyEntity> customStrategyList) {
		Map<Long, CustomStrategyEntity> result = new HashMap<>();
		for(CustomStrategyEntity entity : customStrategyList) {
			Long id = entity.getAssetClass().getId();
			result.put(id, entity);
		}
		return result;
	}

}
