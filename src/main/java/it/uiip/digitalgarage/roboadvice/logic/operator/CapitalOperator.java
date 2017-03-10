package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;

@Service
public class CapitalOperator extends AbstractOperator {
	
	@Autowired
	private PortfolioOperator portfolioOp;
	
	public CapitalResponseDTO getCurrentCapital(UserRegisteredDTO user) {
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

	public boolean computeCapital(UserRegisteredDTO user) {
		CapitalEntity capitalEntity = new CapitalEntity();
		UserEntity userEntity = new UserEntity();
		BigDecimal amount = portfolioOp.evaluatePortfolio(user);
		if(amount == null) {
			return false;
		}
		LocalDate currentDate = LocalDate.now();
		userEntity.setId(user.getId());
		capitalEntity.setUser(userEntity);
		capitalEntity.setAmount(amount);
		capitalEntity.setDate(currentDate);
		CapitalEntity saved = this.capitalRep.findByUserIdAndDate(user.getId(), currentDate);
		if(saved == null) {
			this.capitalRep.save(capitalEntity);
		} else {
			this.capitalRep.updateCapital(user.getId(), currentDate.toString(), amount);
		}
		return true;
	}

	public List<CapitalResponseDTO> getCapitalPeriod(DataRequestDTO request) {
		List<CapitalResponseDTO> response = new ArrayList<CapitalResponseDTO>();
		List<CapitalEntity> entityList;
		if(request.getPeriod() == 0) {
			entityList = this.capitalRep.findByUserId(request.getId());
		}
		else {
			LocalDate initialDate = LocalDate.now();
			LocalDate finalDate = initialDate.minus(Period.ofDays(request.getPeriod() - 1));
			entityList = this.capitalRep.findByUserIdAndDateBetween(request.getId(), finalDate, initialDate);
		}
		if (entityList.isEmpty()) {
			return null;
		}
		for(CapitalEntity entity : entityList){
			CapitalResponseDTO dto = (CapitalResponseDTO) this.capitalConv.convertToDTO(entity);
			response.add(dto);
		}
		Collections.sort(response);
		return  response;
	}
}
