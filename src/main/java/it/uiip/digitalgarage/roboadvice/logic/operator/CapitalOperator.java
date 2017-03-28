package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PeriodDTO;

/**
 * This class manages all the computations related to the Capital.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Service
public class CapitalOperator extends AbstractOperator {
	
	@Autowired
	private PortfolioOperator portfolioOp;

	/**
	 * This method allows to retrieve the current capital for the logged user.
	 *
	 * @param auth	Authentication is used to retrieve the logged user.
	 * @return		CapitalDTO that represents the current capital or null if the users
	 * 				doesn't have a capital.
	 */
	@Cacheable("currentCapital")
	public CapitalDTO getCurrentCapital(Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		CapitalEntity entity = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
		if(entity == null) {
			return null;
		}
		return this.capitalConv.convertToDTO(entity);
	}

	/**
	 * This method allows to retrieve the history of the capital for the logged user in the selected period.
	 *
	 * @param period	PeriodDTO is the number of days to retrieve.
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @return			List of CapitalDTOs or null if the user doesn't have any capital
	 * 					in the selected period.
	 */
	@Cacheable("capitalHistory")
	public List<CapitalDTO> getCapitalPeriod(PeriodDTO period, Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		List<CapitalDTO> response = new ArrayList<CapitalDTO>();
		List<CapitalEntity> capitalList;
		if(period.getPeriod() == 0) {
			capitalList = this.capitalRep.findByUser(user);
		} else {
			LocalDate initialDate = LocalDate.now();
			LocalDate finalDate = initialDate.minus(Period.ofDays(period.getPeriod() - 1));
			capitalList = this.capitalRep.findByUserAndDateBetween(user, finalDate, initialDate);
		}
		if (capitalList.isEmpty()) {
			return null;
		}
		for(CapitalEntity entity : capitalList){
			CapitalDTO dto = (CapitalDTO) this.capitalConv.convertToDTO(entity);
			response.add(dto);
		}
		Collections.sort(response);
		return response;
	}

	/**
	 * This method allows to add an amount to a user capital.
	 *
	 * @param capital	CapitalRequestDTO that contains the amount to add.
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @return			Boolean that is false if some problem occurs, true instead.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public boolean addCapital(CapitalRequestDTO capital, Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		return this.addCapital(capital, user);
	}

	/**
	 * This method allows to add an amount to a user capital.
	 *
	 * @param capital	CapitalRequestDTO that contains the amount to add.
	 * @param user		UserEntity is the selected user.
	 * @return			Boolean that is false if some problem occurs, true instead.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public boolean addCapital(CapitalRequestDTO capital, UserEntity user) {
		CapitalEntity capitalEntity = this.capitalConv.convertToEntity(capital);
		if(user == null) {
			return false;
		}
		capitalEntity.setUser(user);
		capitalEntity.setDate(LocalDate.now());
		CapitalEntity savedCapital = this.capitalRep.findByUserAndDate(user, capitalEntity.getDate());
		if(savedCapital == null) {
			savedCapital = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
			if(savedCapital != null) {
				capitalEntity.setAmount(capitalEntity.getAmount().add(savedCapital.getAmount()));
			}
			this.capitalRep.save(capitalEntity);
		} else {
			BigDecimal newAmount = capitalEntity.getAmount().add(savedCapital.getAmount());
			savedCapital.setAmount(newAmount);
			this.capitalRep.save(savedCapital);
		}
		user.setLastUpdate(LocalDate.now());
		userRep.save(user);
		return true;
	}

	/**
	 * This method compute the capital basing on a capital and the last financial data.
	 *
	 * @param user						User for which you want to compute the capital that contains the current portfolio.
	 * @param financialDataPerAssetMap	Map with asset id as key and a FinancialDataEntity as value.
	 * @return							CapitalEntity that is the new capital computed or null if
	 * 									the user doesn't have any portfolio.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public CapitalEntity computeCapital(User user, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
		CapitalEntity capital = new CapitalEntity();
		BigDecimal amount = portfolioOp.evaluatePortfolio(financialDataPerAssetMap, user.getPortfolio());
		if(amount == null) {
			return null;
		}
		LocalDate currentDate = LocalDate.now();
		capital.setUser(user.getUser());
		capital.setAmount(amount);
		capital.setDate(currentDate);
		CapitalEntity savedCapital = this.capitalRep.findByUserAndDate(user.getUser(), currentDate);
		if(savedCapital == null) {
			this.capitalRep.save(capital);
			user.getUser().setLastUpdate(currentDate);
			this.userRep.save(user.getUser());
			return capital;
		}
		savedCapital.setAmount(amount);
		this.capitalRep.save(savedCapital);
		this.userRep.save(user.getUser());
		return savedCapital;
	}

}
