package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserRegisteredDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CustomStrategyOperator extends AbstractOperator{

    public boolean setCustomStrategy(CustomStrategyDTO request, Authentication auth) {
    	UserEntity user = this.userRep.findByEmail(auth.getName());
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

    public CustomStrategyResponseDTO getActiveUserCustomStrategy(UserRegisteredDTO user){
    	List<CustomStrategyEntity> entityList = this.customStrategyRep.findByUserIdAndActive(user.getId(), true);
    	if(entityList.isEmpty()) {
    		return null;
    	}
    	CustomStrategyResponseDTO result = (CustomStrategyResponseDTO) this.customStrategyWrap.wrapToDTO(entityList);
    	return result;
    }
    
    public List<CustomStrategyResponseDTO> getUserCustomStrategySet(UserRegisteredDTO user){
	List<CustomStrategyEntity> entityList = this.customStrategyRep.findByUserId(user.getId());
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
	return list;
}

}
