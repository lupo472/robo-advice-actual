package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.service.dto.BacktestingDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class BacktestingOperator extends AbstractOperator {

	public List<PortfolioDTO> getBacktesting(BacktestingDTO request, Authentication auth) {
		List<PortfolioDTO> result = new ArrayList<>();
		UserEntity user = this.userRep.findByEmail(auth.getName());
		LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod()));
		List<PortfolioEntity> entityList = createStartingPortfolio(request, user, date);
		PortfolioDTO portfolio = getPortfolio(request, user, entityList);
		System.out.println("Size: " + portfolio.getList().size());
		result.add(portfolio);
		while(!date.isEqual(LocalDate.now())) {
			date = date.plus(Period.ofDays(1));
			for(PortfolioEntity entity: entityList) {
				BigDecimal value = this.getValueForAsset(entity.getUnits(), entity.getAsset(), date);
				entity.setValue(value);
				entity.setDate(date);
			}
			portfolio = getPortfolio(request, user, entityList);
			System.out.println("Size: " + portfolio.getList().size());
			result.add(portfolio);
		}
		Collections.sort(result);
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
		for (CustomStrategyEntity strategy : list) {
			BigDecimal amountPerClass = request.getCapital().divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			AssetClassEntity assetClass = strategy.getAssetClass();
			List<PortfolioEntity> listPerAsset = this.createPortfolioForAssetClass(assetClass, user, amountPerClass, date);
			entityList.addAll(listPerAsset);
		}
		return entityList;
	}

	private List<PortfolioEntity> createPortfolioForAssetClass(AssetClassEntity assetClass, UserEntity user, BigDecimal amount, LocalDate date) {
		List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
		List<PortfolioEntity> entityList = new ArrayList<>();
		for (AssetEntity asset : assets) {
			PortfolioEntity entity = new PortfolioEntity();
			BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 4, RoundingMode.HALF_UP).multiply(asset.getPercentage());
			entity.setAsset(asset);
			entity.setAssetClass(assetClass);
			entity.setUser(user);
			entity.setValue(amountPerAsset);
			entity.setUnits(this.getUnitsForAsset(asset, amountPerAsset, date));
			entity.setDate(date);
			entityList.add(entity);
		}
		return entityList;
	}

	private BigDecimal getUnitsForAsset(AssetEntity asset, BigDecimal amount, LocalDate date) {
		FinancialDataEntity financialData = this.financialDataRep.findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(asset, date);//findByAssetAndDate(asset, date);
		BigDecimal units = amount.divide(financialData.getValue(), 4, RoundingMode.HALF_UP);
		return units;
	}

	private BigDecimal getValueForAsset(BigDecimal units, AssetEntity asset, LocalDate date) {
		FinancialDataEntity financialData = this.financialDataRep.findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(asset, date);
		return units.multiply(financialData.getValue());
	}

}
