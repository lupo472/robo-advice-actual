package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

public class CapitalOperator extends AbstractOperator {
	
	public CapitalOperator(CapitalRepository capitalRep) {
		this.capitalRep = capitalRep;
	}

	public CapitalOperator(CapitalRepository capitalRep, PortfolioRepository portfolioRep, FinancialDataRepository financialDataRep) {
		this.capitalRep = capitalRep;
		this.portfolioRep = portfolioRep;
		this.financialDataRep = financialDataRep;
	}
	
	public CapitalResponseDTO getCurrentCapital(UserLoggedDTO user) {
		CapitalEntity entity = this.capitalRep.findLast(user.getId());
		if(entity == null) {
			return null;
		}
		return (CapitalResponseDTO) this.capitalConv.convertToDTO(entity);
	}
	
	public void addCapital(CapitalDTO capital) {
		CapitalEntity entity = this.capitalConv.convertToEntity(capital);
		entity.setDate(LocalDate.now());
		CapitalEntity saved = this.capitalRep.findByUserIdAndDate(entity.getUser().getId(), entity.getDate());
		if(saved == null) {
			saved = this.capitalRep.findLast(entity.getUser().getId());
			if(saved != null) {
				entity.setAmount(entity.getAmount().add(saved.getAmount()));
			}
			this.capitalRep.save(entity);
		} else {
			BigDecimal newAmount = entity.getAmount().add(saved.getAmount());
			this.capitalRep.updateCapital(entity.getUser().getId(), entity.getDate().toString(), newAmount);
		}
	}

	public boolean computeCapital(UserLoggedDTO user) {
		CapitalEntity capitalEntity = new CapitalEntity();
		UserEntity userEntity = new UserEntity();
		PortfolioOperator portfolioOp = new PortfolioOperator(this.portfolioRep, this.financialDataRep);
		BigDecimal amount = portfolioOp.evaluatePortfolio(user);
		if(amount == null) {
			return false;
		}
		LocalDate currentDate = LocalDate.now();
		userEntity.setId(user.getId());
		capitalEntity.setUser(userEntity);
		capitalEntity.setAmount(amount);
		capitalEntity.setDate(currentDate);
		this.capitalRep.save(capitalEntity);
		return true;
	}
	
}
