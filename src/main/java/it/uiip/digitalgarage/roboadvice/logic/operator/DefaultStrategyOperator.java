package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.*;

import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;

@Service
public class DefaultStrategyOperator extends AbstractOperator {

	public List<DefaultStrategyDTO> getDefaultStrategySet() {
		List<DefaultStrategyEntity> defaultStrategySet = this.defaultStrategyRep.findAll();
		Map<String, List<AssetClassStrategyDTO>> map = new HashMap<>();
		for (DefaultStrategyEntity defaultStrategyEntity : defaultStrategySet) {
			AssetClassStrategyDTO aCSB = new AssetClassStrategyDTO();
			AssetClassDTO assetClass = new AssetClassDTO();
			assetClass.setId(defaultStrategyEntity.getAssetClass().getId());
			assetClass.setName(defaultStrategyEntity.getAssetClass().getName());
			aCSB.setAssetClass(assetClass);
			aCSB.setPercentage(defaultStrategyEntity.getPercentage());
			if(map.get(defaultStrategyEntity.getName()) == null) {
				map.put(defaultStrategyEntity.getName(), new ArrayList<>());
			}
			map.get(defaultStrategyEntity.getName()).add(aCSB);
		}
		List<DefaultStrategyDTO> result = new ArrayList<>();
		for (String key : map.keySet()) {
			DefaultStrategyDTO dSDTO = new DefaultStrategyDTO();
			dSDTO.setName(key);
			Collections.sort(map.get(key));
			dSDTO.setList(map.get(key));
			result.add(dSDTO);
		}

		return result;
	}
	
}
