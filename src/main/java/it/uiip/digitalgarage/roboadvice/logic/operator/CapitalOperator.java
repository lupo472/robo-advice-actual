package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodRequestDTO;

@Service
public class CapitalOperator extends AbstractOperator {
	
	@Autowired
	private PortfolioOperator portfolioOp;

	@Cacheable("currentCapital")
	public CapitalDTO getCurrentCapital(Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		CapitalEntity entity = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
		if(entity == null) {
			return null;
		}
		return this.capitalConv.convertToDTO(entity);
	}

	@Cacheable("capitalHistory")
	public List<CapitalDTO> getCapitalPeriod(PeriodRequestDTO request, Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		List<CapitalDTO> response = new ArrayList<CapitalDTO>();
		List<CapitalEntity> entityList;
		if(request.getPeriod() == 0) {
			entityList = this.capitalRep.findByUser(user);
		} else {
			LocalDate initialDate = LocalDate.now();
			LocalDate finalDate = initialDate.minus(Period.ofDays(request.getPeriod() - 1));
			entityList = this.capitalRep.findByUserAndDateBetween(user, finalDate, initialDate);
		}
		if (entityList.isEmpty()) {
			return null;
		}
		for(CapitalEntity entity : entityList){
			CapitalDTO dto = (CapitalDTO) this.capitalConv.convertToDTO(entity);
			response.add(dto);
		}
		Collections.sort(response);
		return response;
	}

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
	public boolean addCapital(CapitalRequestDTO capital, Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		return this.addCapital(capital, user);
	}

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
	public boolean addCapital(CapitalRequestDTO capital, UserEntity user) {
		CapitalEntity entity = this.capitalConv.convertToEntity(capital);
		if(user == null) {
			return false;
		}
		entity.setUser(user);
		entity.setDate(LocalDate.now());
		CapitalEntity saved = this.capitalRep.findByUserAndDate(user, entity.getDate());
		if(saved == null) {
			saved = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
			if(saved != null) {
				entity.setAmount(entity.getAmount().add(saved.getAmount()));
			}
			this.capitalRep.save(entity);
		} else {
			BigDecimal newAmount = entity.getAmount().add(saved.getAmount());
			saved.setAmount(newAmount);
			this.capitalRep.save(saved);
		}
		user.setLastUpdate(LocalDate.now());
		userRep.save(user);
		return true;
	}

	@CacheEvict(value = {"currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast"}, allEntries = true)
	public CapitalEntity computeCapital(UserEntity user, Map<Long, FinancialDataEntity> map, List<PortfolioEntity> currentPortfolio) {
		CapitalEntity capital = new CapitalEntity();
		BigDecimal amount = portfolioOp.evaluatePortfolio(user, map, currentPortfolio);
		if(amount == null) {
			return null;
		}
		LocalDate currentDate = LocalDate.now();
		capital.setUser(user);
		capital.setAmount(amount);
		capital.setDate(currentDate);
		CapitalEntity saved = this.capitalRep.findByUserAndDate(user, currentDate);
		if(saved == null) {
			this.capitalRep.save(capital);
			user.setLastUpdate(currentDate);
			this.userRep.save(user);
			return capital;
		}
		saved.setAmount(amount);
		this.capitalRep.save(saved);
		this.userRep.save(user);
		return saved;
	}

}
