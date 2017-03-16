package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.logic.operator.PortfolioOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.model.AssetClassValue;
import it.uiip.digitalgarage.roboadvice.persistence.model.TotalValue;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PortfolioWrapper {
	
	@Autowired
	private PortfolioOperator portfolioOp;
	
    public PortfolioDTO wrapToDTO(List<PortfolioEntity> entityList) {
    	PortfolioDTO dto = new PortfolioDTO();
    	List<PortfolioElementDTO> elements = new ArrayList<>();
    	dto.setDate(entityList.get(0).getDate().toString());
    	createPortfolioElement(entityList, elements);
    	dto.setList(elements);
    	return dto;
    }

	private void createPortfolioElement(List<PortfolioEntity> entityList, List<PortfolioElementDTO> elements) { 
		BigDecimal totalValue = this.getTotalValue(entityList);
		Map<Long, List<PortfolioEntity>> map = createMap(entityList);
    	for (Long idAssetClass : map.keySet()) {
    		BigDecimal valueForAssetClass = this.getValueForAssetClass(map, idAssetClass);	
    		PortfolioElementDTO element = new PortfolioElementDTO();
			element.setValue(valueForAssetClass);
			BigDecimal percentage = valueForAssetClass.divide(totalValue, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100.00));
			element.setPercentage(percentage);			
			element.setId(idAssetClass);
			element.setName(map.get(idAssetClass).get(0).getAssetClass().getName());
			elements.add(element);
		}
	}
	
	private BigDecimal getValueForAssetClass(Map<Long, List<PortfolioEntity>> map, Long idAssetClass) {
		AssetClassEntity assetClass = map.get(idAssetClass).get(0).getAssetClass();
		UserEntity user = map.get(idAssetClass).get(0).getUser();
		LocalDate date = map.get(idAssetClass).get(0).getDate();
		AssetClassValue assetClassValue = this.portfolioOp.getAssetClassValue(assetClass, user, date);
		return assetClassValue.getValue();
	}

	private BigDecimal getTotalValue(List<PortfolioEntity> entityList) {
		TotalValue totalValue = this.portfolioOp.getTotalValue(entityList.get(0).getUser(), entityList.get(0).getDate());
		return totalValue.getValue();
	}

	private Map<Long, List<PortfolioEntity>> createMap(List<PortfolioEntity> entityList) {
		Map<Long, List<PortfolioEntity>> map = new HashMap<>();
    	for (PortfolioEntity entity : entityList) {
    		if(map.get(entity.getAssetClass().getId()) == null) {
    			map.put(entity.getAssetClass().getId(), new ArrayList<>());
    		}
			map.get(entity.getAssetClass().getId()).add(entity);
		}
		return map;
	}

}
