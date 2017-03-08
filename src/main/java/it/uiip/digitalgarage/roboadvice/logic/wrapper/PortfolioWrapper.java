package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.logic.converter.AssetClassConverter;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioWrapper implements GenericWrapper<PortfolioEntity, PortfolioDTO> {

    private AssetClassConverter assetClassConv = new AssetClassConverter();

    @Override
    public List<PortfolioEntity> unwrapToEntity(PortfolioDTO dto) {
    	List<PortfolioEntity> entityList = new ArrayList<>();
    	UserEntity user = new UserEntity();
		user.setId(dto.getIdUser());
    	for (PortfolioElementDTO element : dto.getList()) {
			PortfolioEntity entity = new PortfolioEntity();
			entity.setAssetClass(this.assetClassConv.convertToEntity(element.getAssetClassStrategy().getAssetClass()));
			entity.setDate(LocalDate.parse(dto.getDate()));
			entity.setUser(user);
			entity.setValue(element.getValue());
		}
    	return entityList;
    }

    @Override
    public PortfolioDTO wrapToDTO(List<PortfolioEntity> entityList) {
    	PortfolioDTO dto = new PortfolioDTO();
    	List<PortfolioElementDTO> elements = new ArrayList<>();
    	dto.setDate(entityList.get(0).getDate().toString());
    	dto.setIdUser(entityList.get(0).getUser().getId());
    	createPortfolioElement(entityList, elements);
    	dto.setList(elements);
    	return dto;
    }

	private void createPortfolioElement(List<PortfolioEntity> entityList, List<PortfolioElementDTO> elements) {
		Map<Long, List<PortfolioEntity>> map = new HashMap<>();
    	for (PortfolioEntity entity : entityList) {
    		if(map.get(entity.getAssetClass().getId()) == null) {
    			map.put(entity.getAssetClass().getId(), new ArrayList<>());
    		}
			map.get(entity.getAssetClass().getId()).add(entity);
		}
    	for (Long idAssetClass : map.keySet()) {
			BigDecimal sum = new BigDecimal(0);
			PortfolioElementDTO element = new PortfolioElementDTO();
			for (PortfolioEntity entity : map.get(idAssetClass)) {
				sum = sum.add(entity.getValue());
			}
			element.setValue(sum);
			AssetClassStrategyDTO assetClassStrategy = new AssetClassStrategyDTO();
			assetClassStrategy.setAssetClass(this.assetClassConv.convertToDTO(map.get(idAssetClass).get(0).getAssetClass()));
			element.setAssetClassStrategy(assetClassStrategy);
			elements.add(element);
		}
	}

}
