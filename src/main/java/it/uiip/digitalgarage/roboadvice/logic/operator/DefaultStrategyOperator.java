package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.*;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;

@Service
public class DefaultStrategyOperator extends AbstractOperator {

	/*
	 * There is a forcing: the variable risk is 
	 * computed assuming that in the database
	 * the strategies are ordered by risk.
	*/
	@Cacheable("defaultStrategies")
	public List<DefaultStrategyDTO> getDefaultStrategySet() {
		List<DefaultStrategyEntity> defaultStrategySet = this.defaultStrategyRep.findAll();
		Map<String, List<AssetClassStrategyDTO>> map = new HashMap<>();
		Map<String, Integer> riskMap = new HashMap<>();
		int risk = 1;
		for (DefaultStrategyEntity defaultStrategyEntity : defaultStrategySet) {
			AssetClassStrategyDTO aCSB = new AssetClassStrategyDTO();
			aCSB.setId(defaultStrategyEntity.getAssetClass().getId());
			aCSB.setName(defaultStrategyEntity.getAssetClass().getName());
			aCSB.setPercentage(defaultStrategyEntity.getPercentage());
			if(map.get(defaultStrategyEntity.getName()) == null) {
				map.put(defaultStrategyEntity.getName(), new ArrayList<>());
				riskMap.put(defaultStrategyEntity.getName(), risk);
				risk++;
			}
			map.get(defaultStrategyEntity.getName()).add(aCSB);
		}
		List<DefaultStrategyDTO> result = new ArrayList<>();
		for (String key : map.keySet()) {
			DefaultStrategyDTO dSDTO = new DefaultStrategyDTO();
			dSDTO.setName(key);
			dSDTO.setRisk(riskMap.get(key));
			Collections.sort(map.get(key));
			dSDTO.setList(map.get(key));
			result.add(dSDTO);
		}
		Collections.sort(result);
		return result;
	}
	
}
