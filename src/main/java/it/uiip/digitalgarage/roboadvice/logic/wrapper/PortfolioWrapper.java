package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Component
public class PortfolioWrapper {

	@Autowired
	private PortfolioRepository portfolioRep;

	public List<PortfolioDTO> wrapToDTO(UserEntity user, List<PortfolioEntity> entityList) {
		Map<LocalDate, BigDecimal> totalMap = Mapper.getMapValues(this.portfolioRep.sumValues(user));
		Map<Long, Map<LocalDate, BigDecimal>> assetClassMap = new HashMap<>();
		Map<String, Set<PortfolioEntity>> map = this.createMap(user, entityList, assetClassMap);
		List<PortfolioDTO> result = new ArrayList<>();
		for (String date : map.keySet()) {
			PortfolioDTO dto = new PortfolioDTO();
			Map<Long, PortfolioElementDTO> portfolioMap = new HashMap<>();
			for (PortfolioEntity entity : map.get(date)) {
				PortfolioElementDTO element = new PortfolioElementDTO();
				element.setId(entity.getAssetClass().getId());
				element.setName(entity.getAssetClass().getName());
				BigDecimal value = assetClassMap.get(entity.getAssetClass().getId()).get(LocalDate.parse(date));
				BigDecimal percentage = value.divide(totalMap.get(LocalDate.parse(date)), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100.00));
				element.setValue(value);
				element.setPercentage(percentage);
				portfolioMap.put(entity.getAssetClass().getId(), element);
			}
			dto.setDate(date);
			List<PortfolioElementDTO> list = new ArrayList<>(portfolioMap.values());
			Collections.sort(list);
			dto.setList(list);
			result.add(dto);
		}
		return result;
	}

	private Map<String, Set<PortfolioEntity>> createMap(UserEntity user, List<PortfolioEntity> entityList, Map<Long, Map<LocalDate, BigDecimal>> assetClassMap) {
		Map<String, Set<PortfolioEntity>> map = new HashMap<>();
		for (PortfolioEntity entity : entityList) {
			if(assetClassMap.get(entity.getAssetClass().getId()) == null) {
				assetClassMap.put(entity.getAssetClass().getId(), Mapper.getMapValues(this.portfolioRep.sumValuesForAssetClass(entity.getAssetClass(), user)));
			}
			if(map.get(entity.getDate().toString()) == null) {
				map.put(entity.getDate().toString(), new HashSet<>());
			}
			map.get(entity.getDate().toString()).add(entity);
		}
		return map;
	}

}
