package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CapitalRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;

public class CapitalOperator extends AbstractOperator {
	
	public CapitalOperator(CapitalRepository capitalRep) {
		this.capitalRep = capitalRep;
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
	
}
