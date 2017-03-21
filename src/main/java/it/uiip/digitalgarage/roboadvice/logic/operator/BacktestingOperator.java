package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
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

//	public List<PortfolioDTO> getBacktesting(BacktestingDTO request, Authentication auth) {
//		UserEntity user = this.userRep.findByEmail(auth.getName());
//		LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod()));
//		CustomStrategyDTO strategyDTO = new CustomStrategyDTO();
//		strategyDTO.setList(request.getList());
//		List<CustomStrategyEntity> list = this.customStrategyWrap.unwrapToEntity(strategyDTO);
//		BigDecimal amount = request.getCapital();
//		List<PortfolioDTO> result = new ArrayList<>();
//		for (CustomStrategyEntity strategy : list) {
//			BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
//			AssetClassEntity assetClass = strategy.getAssetClass();
//			PortfolioDTO portfolioDTO = this.computePortfolioForAssetClass(assetClass, user, amountPerClass, date);
//			result.add(portfolioDTO);
//		}
//		return result;
//	}
//
//	private PortfolioDTO computePortfolioForAssetClass(AssetClassEntity assetClass, UserEntity user, BigDecimal amount, LocalDate date) {
//		List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
//		List<PortfolioEntity> entityList = new ArrayList<>();
//		for (AssetEntity asset : assets) {
//			PortfolioEntity entity = new PortfolioEntity();
//			BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
//			entity.setAsset(asset);
//			entity.setAssetClass(assetClass);
//			entity.setUser(user);
//			entity.setValue(amountPerAsset);
//			entity.setUnits(this.getUnitsForAsset(asset, amountPerAsset, date));
//			entity.setDate(date);
//			entityList.add(entity);
//		}
//		Map<LocalDate, BigDecimal> totalMap = Mapper.getMapValues(this.portfolioRep.sumValues(user));
//		Map<Long, Map<LocalDate, BigDecimal>> assetClassMap = new HashMap<>();
//		System.out.println("Size: " + entityList.size());
//		System.out.println(entityList.get(0).getValue());
//		PortfolioDTO result = this.portfolioWrap.wrapToDTO(user, entityList);
//		return result;
////		return new ArrayList<>();
//	}
//
//	private BigDecimal getUnitsForAsset(AssetEntity asset, BigDecimal amount, LocalDate date) {
//			FinancialDataEntity financialData = this.financialDataRep.findByAssetAndDate(asset, date);
//			BigDecimal units = amount.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
//			return units;
//	}

}
