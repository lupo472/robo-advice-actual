package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.*;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;

/**
 * This class manages all the computations related to DefaultStrategy.
 *
 * @author Cristian Laurini
 */
@Service
public class DefaultStrategyOperator extends AbstractOperator {

	/**
	 * This method retrieves all the default strategies from the database.
	 * There is a forcing: the variable risk is computed assuming that in the database
	 * the strategies are ordered by risk.
	 *
	 * @return	List of DefaultStrategyDTOs ordered by risk.
	 */
	@Cacheable("defaultStrategies")
	public List<DefaultStrategyDTO> getDefaultStrategySet() {
		List<DefaultStrategyEntity> defaultStrategyList = this.defaultStrategyRep.findAll();
		Map<String, List<AssetClassStrategyDTO>> assetClassStrategyListPerDefaultStrategyMap = new HashMap<>();
		Map<String, Integer> riskPerDefaultStrategyMap = new HashMap<>();
		int risk = 1;
		for (DefaultStrategyEntity defaultStrategyEntity : defaultStrategyList) {
			AssetClassStrategyDTO assetClassStrategy = new AssetClassStrategyDTO();
			assetClassStrategy.setId(defaultStrategyEntity.getAssetClass().getId());
			assetClassStrategy.setName(defaultStrategyEntity.getAssetClass().getName());
			assetClassStrategy.setPercentage(defaultStrategyEntity.getPercentage());
			if(assetClassStrategyListPerDefaultStrategyMap.get(defaultStrategyEntity.getName()) == null) {
				assetClassStrategyListPerDefaultStrategyMap.put(defaultStrategyEntity.getName(), new ArrayList<>());
				riskPerDefaultStrategyMap.put(defaultStrategyEntity.getName(), risk);
				risk++;
			}
			assetClassStrategyListPerDefaultStrategyMap.get(defaultStrategyEntity.getName()).add(assetClassStrategy);
		}
		List<DefaultStrategyDTO> result = new ArrayList<>();
		for (String key : assetClassStrategyListPerDefaultStrategyMap.keySet()) {
			DefaultStrategyDTO dSDTO = new DefaultStrategyDTO();
			dSDTO.setName(key);
			dSDTO.setRisk(riskPerDefaultStrategyMap.get(key));
			Collections.sort(assetClassStrategyListPerDefaultStrategyMap.get(key));
			dSDTO.setList(assetClassStrategyListPerDefaultStrategyMap.get(key));
			result.add(dSDTO);
		}
		Collections.sort(result);
		return result;
	}
	
}
