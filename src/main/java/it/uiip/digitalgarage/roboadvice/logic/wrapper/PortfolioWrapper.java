package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.logic.converter.AssetClassConverter;
import it.uiip.digitalgarage.roboadvice.logic.converter.AssetConverter;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementsDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PortfolioWrapper implements GenericWrapper<PortfolioEntity, PortfolioDTO> {

    private AssetConverter assetConverter = new AssetConverter();
    private AssetClassConverter assetClassConverter = new AssetClassConverter();

    @Override
    public List<PortfolioEntity> unwrapToEntity(PortfolioDTO dto) {
        List<PortfolioEntity> portfolioEntityList = new ArrayList<PortfolioEntity>();

        for(PortfolioElementsDTO element : dto.getElements()){
            PortfolioEntity entity = new PortfolioEntity();
            UserEntity user = new UserEntity();
            AssetEntity asset = new AssetEntity();
            AssetClassEntity assetClass = new AssetClassEntity();

            user.setId(dto.getIdUser());
            asset.setId(element.getAsset().getId());
            assetClass.setId(element.getAsset().getAssetClass().getId());

            entity.setUser(user);
            entity.setAsset(asset);
            entity.setAssetClass(assetClass);
            entity.setUnits(element.getUnits());
            entity.setValue(element.getValue());
        }

        return portfolioEntityList;
    }

    @Override
    public PortfolioDTO wrapToDTO(List<PortfolioEntity> entityList) {
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
