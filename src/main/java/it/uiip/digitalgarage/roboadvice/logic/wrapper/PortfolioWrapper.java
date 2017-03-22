package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
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

	public List<PortfolioDTO> wrapToDTOList(UserEntity user, List<PortfolioEntity> entityList,
											Map<Long, Map<LocalDate, BigDecimal>> assetClassMap,
											Map<String, Set<PortfolioEntity>> map,
											Map<LocalDate, BigDecimal> totalMap) {
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

	public PortfolioDTO wrapToDTO(UserEntity user, List<PortfolioEntity> entityList, BigDecimal total,
								  Map<Long, BigDecimal> assetClassMap) {
		PortfolioDTO result = new PortfolioDTO();
		LocalDate date = entityList.get(0).getDate();
		result.setDate(date.toString());
		Map<Long, PortfolioElementDTO> map = new HashMap<>();
		for (PortfolioEntity entity : entityList) {
			BigDecimal assetClassValue = assetClassMap.get(entity.getAssetClass().getId());
			PortfolioElementDTO element = new PortfolioElementDTO();
			element.setId(entity.getAssetClass().getId());
			element.setName(entity.getAssetClass().getName());
			element.setValue(assetClassValue);
			element.setPercentage(assetClassValue.divide(total, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100.00)));
			map.put(element.getId(), element);
		}
		List<PortfolioElementDTO> list = new ArrayList<>(map.values());
		Collections.sort(list);
		result.setList(list);
		return result;
	}

}
