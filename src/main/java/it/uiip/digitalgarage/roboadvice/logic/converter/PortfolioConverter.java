package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;

import java.util.List;

public class PortfolioConverter implements GenericConverter<PortfolioEntity,PortfolioDTO>{

    @Override
    public PortfolioEntity convertToEntity(PortfolioDTO dto){

        PortfolioEntity portfolioEntity = new PortfolioEntity();
        UserEntity user = new UserEntity();
        AssetEntity asset = new AssetEntity();
        AssetClassEntity assetClass = new AssetClassEntity();

        user.setId(dto.getIdUser());
        asset.setId(dto.getIdAsset());
        assetClass.setId(dto.getIdAssetClass());

        portfolioEntity.setIdUser(user);
        portfolioEntity.setIdAsset(asset);
        portfolioEntity.setIdAssetClass(assetClass);
        portfolioEntity.setUnits(dto.getUnits());
        portfolioEntity.setValue(dto.getValue());
        portfolioEntity.setDate(dto.getDate().toString());


        return portfolioEntity;
    }

    @Override
    public PortfolioDTO convertToDTO(PortfolioEntity entity) {
        return null;
    }

    @Override
    public List<PortfolioEntity> convertToEntity(List<PortfolioDTO> dto) {
        return null;
    }

    @Override
    public List<PortfolioDTO> convertToDTO(List<PortfolioEntity> entity) {
        return null;
    }

}
