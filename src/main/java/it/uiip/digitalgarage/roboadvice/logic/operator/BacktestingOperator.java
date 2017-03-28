package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.User;
import it.uiip.digitalgarage.roboadvice.persistence.util.Value;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * This class contains methods to compute the Backtesting.
 *
 * @author Cristian Laurini
 */
@Service
public class BacktestingOperator extends AbstractOperator {

	@Autowired
	private AssetClassOperator assetClassOp;

	@Autowired
	private RebalancingOperator rebalancingOp;

	/**
	 * This method compute the backtesting for the logged user based on the BacktestingDTO requested.
	 *
	 * @param request	BacktestingDTO that contains the strategy, the period and the capital selected.
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @return			List of PortfolioDTOs that represents the backtesting or null if the backtesting
	 * 					isn't applicable for the selected period and strategy.
	 */
	@Cacheable("backtesting")
	public List<PortfolioDTO> getBacktesting(BacktestingDTO request, Authentication auth) {
		List<PortfolioDTO> result = new ArrayList<>();
		UserEntity userEntity = this.userRep.findByEmail(auth.getName());
		User user = new User();
		user.setUser(userEntity);
		LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod() - 1));
		CustomStrategyDTO strategyDTO = new CustomStrategyDTO();
		strategyDTO.setList(request.getList());
		List<CustomStrategyEntity> strategyList = this.customStrategyWrap.unwrapToEntity(strategyDTO);
		user.setStrategy(strategyList);
		Map<Long, List<FinancialDataEntity>> financialDataPerAssetMap = new HashMap<>();
		Map<Long, List<AssetEntity>> assetsPerClassMap = new HashMap<>();
		this.createMaps(date, strategyList, financialDataPerAssetMap, assetsPerClassMap);
		List<PortfolioEntity> portfolioList = createStartingPortfolio(user, request, date, financialDataPerAssetMap, assetsPerClassMap);
		user.setPortfolio(portfolioList);
		if(portfolioList == null) {
			return null;
		}
		PortfolioDTO portfolio = getPortfolio(request, user);
		result.add(portfolio);
		while(!date.isEqual(LocalDate.now())) {
			date = date.plus(Period.ofDays(1));
			Map<Long, FinancialDataEntity> financialDataPerAssetMapPerDate = new HashMap<>();
			for(PortfolioEntity portfolioEntity: portfolioList) {
				AssetEntity asset = portfolioEntity.getAsset();
				List<FinancialDataEntity> financialDataList = financialDataPerAssetMap.get(asset.getId());
				FinancialDataEntity financialData = getFinancialData(date, asset, financialDataList);
				financialDataPerAssetMapPerDate.put(asset.getId(), financialData);
				BigDecimal value = this.getValueForAsset(portfolioEntity.getUnits(), financialData);
				portfolioEntity.setValue(value);
				portfolioEntity.setDate(date);
			}
			portfolioList = this.rebalancingOp.rebalance(user, financialDataPerAssetMapPerDate);
			portfolio = getPortfolio(request, user);
			result.add(portfolio);
		}
		Collections.sort(result);
		return result;
	}

	private List<PortfolioEntity> createStartingPortfolio(User user, BacktestingDTO request, LocalDate date, Map<Long, List<FinancialDataEntity>> financialDataPerAssetMap, Map<Long, List<AssetEntity>> assetsPerClassMap) {
		List<PortfolioEntity> portfolioList = new ArrayList<>();
		for (CustomStrategyEntity strategy : user.getStrategy()) {
			List<AssetEntity> assetsPerClass = assetsPerClassMap.get(strategy.getAssetClass().getId());
			BigDecimal amountPerClass = request.getCapital().divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			Value value =  new Value(date, amountPerClass);
			List<PortfolioEntity> portfolioListPerAsset = this.createPortfolioForAssetClass(assetsPerClass, user.getUser(), value, financialDataPerAssetMap);
			if(portfolioListPerAsset == null) {
				return null;
			}
			portfolioList.addAll(portfolioListPerAsset);
		}
		return portfolioList;
	}

	private void createMaps(LocalDate date, List<CustomStrategyEntity> strategyList, Map<Long, List<FinancialDataEntity>> financialDataPerAssetMap, Map<Long, List<AssetEntity>> assetsPerClassMap) {
		for(CustomStrategyEntity strategy : strategyList) {
			List<AssetEntity> assetsPerClass = this.assetRep.findByAssetClass(strategy.getAssetClass());
			assetsPerClassMap.put(strategy.getAssetClass().getId(), assetsPerClass);
			for(AssetEntity asset : assetsPerClass) {
				List<FinancialDataEntity> financialDataPerAsset = this.financialDataRep.findByAssetAndDateGreaterThanOrderByDateDesc(asset, date);
				financialDataPerAssetMap.put(asset.getId(), financialDataPerAsset);
			}
		}
	}

	private List<PortfolioEntity> createPortfolioForAssetClass(List<AssetEntity> assets, UserEntity user, Value value, Map<Long, List<FinancialDataEntity>> financialDataPerAssetMap) {
		List<PortfolioEntity> portfolioList = new ArrayList<>();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> financialDataList = financialDataPerAssetMap.get(asset.getId());
			FinancialDataEntity financialData = getFinancialData(value.getDate(), asset, financialDataList);
			if(financialData == null) {
				return null;
			}
			BigDecimal amountPerAsset = value.getValue().divide(new BigDecimal(100.00), 4, RoundingMode.HALF_UP).multiply(asset.getPercentage());
			BigDecimal units = this.getUnitsForAsset(financialData, amountPerAsset);
			PortfolioEntity entity = new PortfolioEntity();
			entity.setAsset(asset);
			entity.setAssetClass(asset.getAssetClass());
			entity.setUser(user);
			entity.setValue(amountPerAsset);
			entity.setUnits(units);
			entity.setDate(value.getDate());
			portfolioList.add(entity);
		}
		return portfolioList;
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

	private PortfolioDTO getPortfolio(BacktestingDTO request, User user) {
		Map<Long, BigDecimal> assetClassMap = new HashMap<>();
		BigDecimal total = new BigDecimal(0);
		for(PortfolioEntity entity : user.getPortfolio()) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), new BigDecimal(0));
			}
			assetClassMap.put(entity.getAssetClass().getId(), assetClassMap.get(entity.getAssetClass().getId()).add(entity.getValue()));
			total = total.add(entity.getValue());
		}
		return this.portfolioWrap.wrapToDTO(user.getPortfolio(), total, assetClassMap);
	}

	private BigDecimal getUnitsForAsset(FinancialDataEntity financialData, BigDecimal amount) {
		BigDecimal units = amount.divide(financialData.getValue(), 4, RoundingMode.HALF_UP);
		return units;
	}

	private BigDecimal getValueForAsset(BigDecimal units, FinancialDataEntity financialData) {
		return units.multiply(financialData.getValue());
	}

	/**
	 * This method returns the minimum date for which is applicable the selected strategy.
	 *
	 * @param request	CustomStrategyDTO is the strategy for which compute the date.
	 * @return			String that is the minimum date for the strategy.
	 */
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

