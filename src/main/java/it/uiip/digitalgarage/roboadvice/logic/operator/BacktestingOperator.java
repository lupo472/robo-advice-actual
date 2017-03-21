package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.BacktestingDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public class BacktestingOperator extends AbstractOperator {

	public List<PortfolioDTO> getBacktesting(BacktestingDTO request, Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());



		return null;
	}

}
