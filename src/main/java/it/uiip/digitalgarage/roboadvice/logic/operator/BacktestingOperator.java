package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.BacktestingDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class BacktestingOperator extends AbstractOperator {

	public List<PortfolioDTO> getBacktesting(BacktestingDTO request, Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod()));
		CustomStrategyDTO strategyDTO = new CustomStrategyDTO();
		strategyDTO.setList(request.getList());
		List<CustomStrategyEntity> list = this.customStrategyWrap.unwrapToEntity(strategyDTO);
		BigDecimal amount = request.getCapital();
		for (CustomStrategyEntity strategy : list) {
			BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			AssetClassEntity assetClass = strategy.getAssetClass();
			//this.savePortfolioForAssetClass(assetClass, user, amountPerClass);
		}
		return null;
	}



}
