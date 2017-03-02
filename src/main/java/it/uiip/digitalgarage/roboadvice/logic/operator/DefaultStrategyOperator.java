package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;

public class DefaultStrategyOperator extends GenericOperator {

	public DefaultStrategyOperator(DefaultStrategyRepository defaultStrategyRep) {
		this.defaultStrategyRep = defaultStrategyRep;
	}
	
	public List<DefaultStrategyEntity> getDefaultStrategySet() {
		return this.defaultStrategyRep.findAll();
	}
	
}
