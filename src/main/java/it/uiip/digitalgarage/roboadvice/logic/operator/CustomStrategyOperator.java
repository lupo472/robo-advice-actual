package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * This class manages all the computations related to CustomStrategy.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Service
public class CustomStrategyOperator extends AbstractOperator{

	/**
	 * This method set a new strategy for the logged user.
	 *
	 * @param strategy	CustomStrategyDTO that is the selected strategy.
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @return			Boolean that is true if everything is ok, false if some problem occurs.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
    public boolean setCustomStrategy(CustomStrategyDTO strategy, Authentication auth) {
    	UserEntity user = this.userRep.findByEmail(auth.getName());
    	return this.setCustomStrategy(strategy, user);
    }

	/**
	 * This method set a new strategy for the selected user.
	 *
	 * @param strategy	CustomStrategyDTO that is the selected strategy.
	 * @param user		UserEntity is the user for which you want to set the strategy.
	 * @return			Boolean that is true if everything is ok, false if some problem occurs.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
    public boolean setCustomStrategy(CustomStrategyDTO strategy, UserEntity user) {
		if(user == null){
			return false;
		}
		this.customStrategyRep.setStrategyInactive(user);
		List<CustomStrategyEntity> todayStrategySet = this.customStrategyRep.findByUserAndDate(user, LocalDate.now());
		if(todayStrategySet.size() > 0) {
			this.customStrategyRep.delete(todayStrategySet);
		}
		List<CustomStrategyEntity> strategyEntityList = this.customStrategyWrap.unwrapToEntity(strategy);
		for (CustomStrategyEntity strategyEntity : strategyEntityList) {
			strategyEntity.setUser(user);
			strategyEntity.setActive(true);
			strategyEntity.setDate(LocalDate.now());
		}
		this.customStrategyRep.save(strategyEntityList);
		return true;
	}

	/**
	 * This method retrieves the active strategy for the logged user.
	 *
	 * @param auth	Authentication is used to retrieve the logged user.
	 * @return		CustomStrategyResponseDTO or null if the user doesn't have any active strategy.
	 */
	@Cacheable("activeStrategy")
    public CustomStrategyResponseDTO getActiveStrategy(Authentication auth){
    	UserEntity user = this.userRep.findByEmail(auth.getName());
    	return this.getActiveStrategy(user);
    }

	/**
	 * This method retrieves the active strategy for the selected user.
	 *
	 * @param user	UserEntity is the user for which you want to retrieve the active strategy.
	 * @return		CustomStrategyResponseDTO or null if the user doesn't have any active strategy.
	 */
	@Cacheable("activeStrategy")
	public CustomStrategyResponseDTO getActiveStrategy(UserEntity user) {
    	List<CustomStrategyEntity> strategyList = this.customStrategyRep.findByUserAndActive(user, true);
    	if(strategyList.isEmpty()) {
    		return null;
    	}
    	CustomStrategyResponseDTO result = (CustomStrategyResponseDTO) this.customStrategyWrap.wrapToDTO(strategyList);
    	return result;
    }

	/**
	 * This method retrieves all the strategies for the logged user in the selected period.
	 *
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @param period	PeriodDTO is the number of days to retrieve.
	 * @return			List of CustomStrategyResponseDTOs.
	 */
    @Cacheable("strategies")
    public List<CustomStrategyResponseDTO> getCustomStrategySet(Authentication auth, int period) {
    	UserEntity user = this.userRep.findByEmail(auth.getName());
    	return this.getCustomStrategySet(user, period);
    }

	/**
	 * This methods retrieves all the strategies for the selected user in the selected period.
	 *
	 * @param user		UserEntity is the user for which you want to retrieve the strategies.
	 * @param period	PeriodDTO is the number of days to retrieve.
	 * @return			List of CustomStrategyResponseDTOs.
	 */
	@Cacheable("strategies")
    public List<CustomStrategyResponseDTO> getCustomStrategySet(UserEntity user, int period){
    	List<CustomStrategyEntity> strategyList = new ArrayList<>();
    	if(period == 0) {
    		strategyList = this.customStrategyRep.findByUser(user);
    	} else {
    		LocalDate start = LocalDate.now().minus(Period.ofDays(period - 1));
    		strategyList = this.customStrategyRep.findByUserAndDateBetween(user, start, LocalDate.now());
    	}
		Map<String, List<CustomStrategyEntity>> strategyListPerDate = new HashMap<>();
	    for (CustomStrategyEntity strategy : strategyList) {
			if(strategyListPerDate.get(strategy.getDate().toString()) == null) {
				strategyListPerDate.put(strategy.getDate().toString(), new ArrayList<>());
			}
			strategyListPerDate.get(strategy.getDate().toString()).add(strategy);
		}
	    List<CustomStrategyResponseDTO> result = new ArrayList<>();
    	for (String date : strategyListPerDate.keySet()) {
			CustomStrategyResponseDTO dto = this.customStrategyWrap.wrapToDTO(strategyListPerDate.get(date));
			result.add(dto);
		}
    	Collections.sort(result);
		return result;
    }

}
