package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
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

	private int count; //TODO remove declaration

	@Cacheable("backtesting")
	public List<PortfolioDTO> getBacktesting(BacktestingDTO request, Authentication auth) {
		this.count = 0; //TODO remove start
		List<PortfolioDTO> result = new ArrayList<>();
		UserEntity user = this.userRep.findByEmail(auth.getName());
		this.count++; //TODO remove necessary
		LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod()));
		List<PortfolioEntity> entityList = createStartingPortfolio(request, user, date);
		if(entityList == null) {
			return null;
		}
		PortfolioDTO portfolio = getPortfolio(request, user, entityList);
		result.add(portfolio);
		while(!date.isEqual(LocalDate.now())) {
			date = date.plus(Period.ofDays(1));
			for(PortfolioEntity entity: entityList) {
				BigDecimal value = this.getValueForAsset(entity.getUnits(), entity.getAsset(), date);
				entity.setValue(value);
				entity.setDate(date);
			}
			portfolio = getPortfolio(request, user, entityList);
			result.add(portfolio);
		}
		Collections.sort(result);
		System.out.println("Query: " + count);
		return result;
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

	private List<PortfolioEntity> createStartingPortfolio(BacktestingDTO request, UserEntity user, LocalDate date) {
		CustomStrategyDTO strategyDTO = new CustomStrategyDTO();
		strategyDTO.setList(request.getList());
		List<CustomStrategyEntity> list = this.customStrategyWrap.unwrapToEntity(strategyDTO);
		List<PortfolioEntity> entityList = new ArrayList<>();
		List<AssetEntity> assets = this.assetRep.findAll();
		Map<Long, List<AssetEntity>> mapAssets = Mapper.getMapAssets(assets);
		for (CustomStrategyEntity strategy : list) {
			BigDecimal amountPerClass = request.getCapital().divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			AssetClassEntity assetClass = strategy.getAssetClass();
			List<AssetEntity> assetsPerClass = mapAssets.get(assetClass.getId());
			List<PortfolioEntity> listPerAsset = this.createPortfolioForAssetClass(assetsPerClass, user, amountPerClass, date);
			if(listPerAsset == null) {
				return null;
			}
			entityList.addAll(listPerAsset);
		}
		return entityList;
	}

	private List<PortfolioEntity> createPortfolioForAssetClass(List<AssetEntity> assets, UserEntity user, BigDecimal amount, LocalDate date) {
		List<PortfolioEntity> entityList = new ArrayList<>();
		for (AssetEntity asset : assets) {
			BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 4, RoundingMode.HALF_UP).multiply(asset.getPercentage());
			BigDecimal units = this.getUnitsForAsset(asset, amountPerAsset, date);
			if(units == null) {
				return null;
			}
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

	private BigDecimal getUnitsForAsset(AssetEntity asset, BigDecimal amount, LocalDate date) {
		FinancialDataEntity financialData = this.financialDataRep.findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(asset, date); //TODO posso prendere i valori tutti insieme (almeno raggruppati per data) e inserirli in una mappa. Meglio ancora mettere tutto in una mappa di una mappa e al limite prendere l'ultimo valore
		this.count++; //TODO remove
		if(financialData == null) {
			return null;
		}
		BigDecimal units = amount.divide(financialData.getValue(), 4, RoundingMode.HALF_UP);
		return units;
	}

	private BigDecimal getValueForAsset(BigDecimal units, AssetEntity asset, LocalDate date) {
		FinancialDataEntity financialData = this.financialDataRep.findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(asset, date); //TODO questa chiamata è identica a quella del metodo getUnitsForAsset
		this.count++; //TODO remove
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
