package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementsDTO;

import java.util.ArrayList;
import java.util.List;

public class PortfolioConverter{

    private AssetConverter assetConverter = new AssetConverter();
    private AssetClassConverter assetClassConverter = new AssetClassConverter();

    public PortfolioDTO convertToDTO(List<PortfolioEntity> entityList) {
        PortfolioDTO dto = new PortfolioDTO();
        List<PortfolioElementsDTO> dtoList = new ArrayList<PortfolioElementsDTO>();

        for (PortfolioEntity entity : entityList) {
            PortfolioElementsDTO element = new PortfolioElementsDTO();
            element.setAsset(this.assetConverter.convertToDTO(entity.getAsset()));
            element.setUnits(entity.getUnits());
            element.setValue(entity.getValue());
            dtoList.add(element);
        }
        dto.setElements(dtoList);

        return dto;
    }


}
