package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.logic.converter.AssetConverter;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementsDTO;

import java.util.ArrayList;
import java.util.List;

public class PortfolioWrapper implements GenericWrapper<PortfolioEntity, PortfolioDTO> {

    private AssetConverter assetConv = new AssetConverter();

    @Override
    public List<PortfolioEntity> unwrapToEntity(PortfolioDTO portfolioDTO) {

        return null;
    }

    @Override
    public PortfolioDTO wrapToDTO(List<PortfolioEntity> entityList) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setList(new ArrayList<>());
        dto.setIdUser(entityList.get(0).getId());
        dto.setDate(entityList.get(0).getDate().toString());
        for (PortfolioEntity entity : entityList) {
            PortfolioElementsDTO element = new PortfolioElementsDTO();
            element.setAsset(this.assetConv.convertToDTO(entity.getAsset()));
            element.setUnits(entity.getUnits());
            element.setValue(entity.getValue());
            dto.getList().add(element);
        }
        return dto;
    }




}
