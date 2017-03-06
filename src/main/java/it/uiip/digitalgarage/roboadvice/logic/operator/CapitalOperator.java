package it.uiip.digitalgarage.roboadvice.logic.operator;

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
		if(this.capitalRep.findByUserIdAndDate(entity.getUser().getId(), entity.getDate()) == null) {
			this.capitalRep.save(entity);
		} else {
			this.capitalRep.updateCapital(entity.getUser().getId(), entity.getDate());
		}
	}
	
}
