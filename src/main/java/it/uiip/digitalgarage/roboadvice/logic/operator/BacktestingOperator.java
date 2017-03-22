package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.BacktestingDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class BacktestingOperator extends AbstractOperator {

	@Autowired
	private AssetClassOperator assetClassOp;

	@Cacheable("backtesting")
	public List<PortfolioDTO> getBacktesting(BacktestingDTO request, Authentication auth) {
		List<PortfolioDTO> result = new ArrayList<>();
		UserEntity user = this.userRep.findByEmail(auth.getName());
		LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod() - 1));
		CustomStrategyDTO strategyDTO = new CustomStrategyDTO();
		strategyDTO.setList(request.getList());
		List<CustomStrategyEntity> strategyList = this.customStrategyWrap.unwrapToEntity(strategyDTO);
		Map<Long, List<FinancialDataEntity>> financialDataMap = new HashMap<>();
		Map<Long, List<AssetEntity>> assetMap = new HashMap<>();
		createMaps(date, strategyList, financialDataMap, assetMap);
		List<PortfolioEntity> entityList = createStartingPortfolio(request, user, date, strategyList, financialDataMap, assetMap);
		if(entityList == null) {
			return null;
		}
		PortfolioDTO portfolio = getPortfolio(request, user, entityList);
		result.add(portfolio);
		while(!date.isEqual(LocalDate.now())) {
			date = date.plus(Period.ofDays(1));
			for(PortfolioEntity entity: entityList) {
				AssetEntity asset = entity.getAsset();
				List<FinancialDataEntity> financialDataList = financialDataMap.get(asset.getId());
				FinancialDataEntity financialData = getFinancialData(date, asset, financialDataList);
				BigDecimal value = this.getValueForAsset(entity.getUnits(), financialData);
				entity.setValue(value);
				entity.setDate(date);
			}
			portfolio = getPortfolio(request, user, entityList);
			result.add(portfolio);
		}
		Collections.sort(result);
		return result;
	}

	private List<PortfolioEntity> createStartingPortfolio(BacktestingDTO request, UserEntity user, LocalDate date, List<CustomStrategyEntity> strategyList, Map<Long, List<FinancialDataEntity>> financialDataMap, Map<Long, List<AssetEntity>> assetMap) {
		List<PortfolioEntity> entityList = new ArrayList<>();
		for (CustomStrategyEntity strategy : strategyList) {
			List<AssetEntity> assetsPerClass = assetMap.get(strategy.getAssetClass().getId());
			BigDecimal amountPerClass = request.getCapital().divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			List<PortfolioEntity> listPerAsset = this.createPortfolioForAssetClass(assetsPerClass, user, amountPerClass, date, financialDataMap);
			if(listPerAsset == null) {
				return null;
			}
			entityList.addAll(listPerAsset);
		}
		return entityList;
	}

	private void createMaps(LocalDate date, List<CustomStrategyEntity> list, Map<Long, List<FinancialDataEntity>> financialDataMap, Map<Long, List<AssetEntity>> assetMap) {
		for(CustomStrategyEntity strategy : list) {
			List<AssetEntity> assetsPerClass = this.assetRep.findByAssetClass(strategy.getAssetClass());
			assetMap.put(strategy.getAssetClass().getId(), assetsPerClass);
			for(AssetEntity asset : assetsPerClass) {
				List<FinancialDataEntity> financialDataPerAsset = this.financialDataRep.findByAssetAndDateGreaterThanOrderByDateDesc(asset, date);
				financialDataMap.put(asset.getId(), financialDataPerAsset);
			}
		}
	}

	private List<PortfolioEntity> createPortfolioForAssetClass(List<AssetEntity> assets, UserEntity user, BigDecimal amount, LocalDate date,
															   Map<Long, List<FinancialDataEntity>> financialDataMap) {
		List<PortfolioEntity> entityList = new ArrayList<>();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> financialDataList = financialDataMap.get(asset.getId());
			FinancialDataEntity financialData = getFinancialData(date, asset, financialDataList);
			if(financialData == null) {
				return null;
			}
			BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 4, RoundingMode.HALF_UP).multiply(asset.getPercentage());
			BigDecimal units = this.getUnitsForAsset(financialData, amountPerAsset);
			PortfolioEntity entity = new PortfolioEntity();
			entity.setAsset(asset);
			entity.setAssetClass(asset.getAssetClass());
			entity.setUser(user);
			entity.setValue(amountPerAsset);
			entity.setUnits(units);
			entity.setDate(date);
			entityList.add(entity);
		}
		return entityList;
	}

	private FinancialDataEntity getFinancialData(LocalDate date, AssetEntity asset, List<FinancialDataEntity> financialDataList) {
		FinancialDataEntity financialData = null;
		if(!financialDataList.isEmpty()) {
			financialData = financialDataList.get(financialDataList.size() - 1);
			if(financialDataList.size() > 1 && financialDataList.get(financialDataList.size() - 2).getDate().isEqual(date)) {
				financialDataList.remove(financialData);
				financialData = financialDataList.get(financialDataList.size() - 1);
			}
		}
		if(financialData == null || financialData.getDate().isAfter(date)) {
			financialData = this.financialDataRep.findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(asset, date);
		}
		if(financialData == null) {
			return null;
		}
		return financialData;
	}

	private PortfolioDTO getPortfolio(BacktestingDTO request, UserEntity user, List<PortfolioEntity> entityList) {
		Map<Long, BigDecimal> mapPerAsset = new HashMap<>();
		BigDecimal total = new BigDecimal(0);
		for(PortfolioEntity entity : entityList) {
			if(mapPerAsset.get(entity.getAssetClass().getId()) == null) {
				mapPerAsset.put(entity.getAssetClass().getId(), new BigDecimal(0));
			}
			mapPerAsset.put(entity.getAssetClass().getId(), mapPerAsset.get(entity.getAssetClass().getId()).add(entity.getValue()));
			total = total.add(entity.getValue());
		}
		return this.portfolioWrap.wrapToDTO(user, entityList, total, mapPerAsset);
	}

	private BigDecimal getUnitsForAsset(FinancialDataEntity financialData, BigDecimal amount) {
		BigDecimal units = amount.divide(financialData.getValue(), 4, RoundingMode.HALF_UP);
		return units;
	}

	private BigDecimal getValueForAsset(BigDecimal units, FinancialDataEntity financialData) {
		return units.multiply(financialData.getValue());
	}

	@Cacheable("minimumBacktestingDate")
	public String getMinimumBacktestingDate(CustomStrategyDTO request) {
		LocalDate date = null;
		for(AssetClassStrategyDTO assetClassStrategy : request.getList()) {
			AssetClassEntity assetClass = new AssetClassEntity();
			assetClass.setId(assetClassStrategy.getId());
			assetClass.setName(assetClassStrategy.getName());
			LocalDate assetClassDate = this.assetClassOp.getMinDate(assetClass);
			if(date == null || date.isBefore(assetClassDate)) {
				date = assetClassDate;
			}
		}
		return date.toString();
	}

}

