package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;

public class DefaultStrategyOperator extends AbstractOperator {

	public DefaultStrategyOperator(DefaultStrategyRepository defaultStrategyRep) {
		this.defaultStrategyRep = defaultStrategyRep;
	}
	
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
			dSDTO.setList(map.get(key));
			result.add(dSDTO);
		}
		return result;
	}
	
}
