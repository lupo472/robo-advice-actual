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

@Service
public class CustomStrategyOperator extends AbstractOperator{

	@CacheEvict(value = {"activeStrategy", "strategies"}, allEntries = true)
    public boolean setCustomStrategy(CustomStrategyDTO request, Authentication auth) {
    	UserEntity user = this.userRep.findByEmail(auth.getName());
    	return this.setCustomStrategy(request, user);
    }

	@CacheEvict(value = {"activeStrategy", "strategies"}, allEntries = true)
    public boolean setCustomStrategy(CustomStrategyDTO request, UserEntity user) {
		if(user == null){
			return false;
		}
		this.customStrategyRep.setStrategyInactive(user);
		List<CustomStrategyEntity> todayStrategySet = this.customStrategyRep.findByUserAndDate(user, LocalDate.now());
		if(todayStrategySet.size() > 0) {
			this.customStrategyRep.delete(todayStrategySet);
		}
		List<CustomStrategyEntity> entityList = this.customStrategyWrap.unwrapToEntity(request);
		for (CustomStrategyEntity entity : entityList) {
			entity.setUser(user);
			entity.setActive(true);
			entity.setDate(LocalDate.now());
		}
		this.customStrategyRep.save(entityList);
		return true;
	}

	@Cacheable("activeStrategy")
    public CustomStrategyResponseDTO getActiveStrategy(Authentication auth){
    	UserEntity user = this.userRep.findByEmail(auth.getName());
    	return this.getActiveStrategy(user);
    }

	@Cacheable("activeStrategy")
	public CustomStrategyResponseDTO getActiveStrategy(UserEntity user) {
    	List<CustomStrategyEntity> entityList = this.customStrategyRep.findByUserAndActive(user, true);
    	if(entityList.isEmpty()) {
    		return null;
    	}
    	CustomStrategyResponseDTO result = (CustomStrategyResponseDTO) this.customStrategyWrap.wrapToDTO(entityList);
    	return result;
    }

    @Cacheable("strategies")
    public List<CustomStrategyResponseDTO> getCustomStrategySet(Authentication auth, int period) {
    	UserEntity user = this.userRep.findByEmail(auth.getName());
    	return this.getCustomStrategySet(user, period);
    }

	@Cacheable("strategies")
    public List<CustomStrategyResponseDTO> getCustomStrategySet(UserEntity user, int period){
    	List<CustomStrategyEntity> entityList = new ArrayList<>();
    	if(period == 0) {
    		entityList = this.customStrategyRep.findByUser(user);
    	} else {
    		LocalDate start = LocalDate.now().minus(Period.ofDays(period - 1));
    		entityList = this.customStrategyRep.findByUserAndDateBetween(user, start, LocalDate.now());
    	}
		Map<String, List<CustomStrategyEntity>> map = new HashMap<>();
	    for (CustomStrategyEntity entity : entityList) {
			if(map.get(entity.getDate().toString()) == null) {
				map.put(entity.getDate().toString(), new ArrayList<>());
			}
			map.get(entity.getDate().toString()).add(entity);
		}
	    List<CustomStrategyResponseDTO> list = new ArrayList<>();
    	for (String date : map.keySet()) {
			CustomStrategyResponseDTO dto = (CustomStrategyResponseDTO) this.customStrategyWrap.wrapToDTO(map.get(date));
			list.add(dto);
		}
    	Collections.sort(list);
		return list;
    }

}
