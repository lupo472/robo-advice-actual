package it.uiip.digitalgarage.roboadvice.logic.converter;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;

import java.util.ArrayList;
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

        portfolioEntity.setUser(user);
        portfolioEntity.setAsset(asset);
        portfolioEntity.setAssetClass(assetClass);
        portfolioEntity.setUnits(dto.getUnits());
        portfolioEntity.setValue(dto.getValue());
        portfolioEntity.setDate(dto.getDate().toString());


        return portfolioEntity;
    }

    @Override
    public PortfolioDTO convertToDTO(PortfolioEntity entity) {

        PortfolioDTO portfolioDTO = new PortfolioDTO();

        portfolioDTO.setIdUser(entity.getUser().getId());
        portfolioDTO.setIdAsset(entity.getAsset().getId());
        portfolioDTO.setIdAssetClass(entity.getAssetClass().getId());
        portfolioDTO.setUnits(entity.getUnits());
        portfolioDTO.setValue(entity.getValue());
        portfolioDTO.setDate(entity.getDate());

        return portfolioDTO;
    }

    @Override
    public List<PortfolioEntity> convertToEntity(List<PortfolioDTO> dto) {
        List<PortfolioEntity> entityList = new ArrayList<PortfolioEntity>();

        for(PortfolioDTO portfolioDTO : dto )
            entityList.add(this.convertToEntity(portfolioDTO));

        return entityList;
    }

    @Override
    public List<PortfolioDTO> convertToDTO(List<PortfolioEntity> entity) {
        List<PortfolioDTO> entityDTOList = new ArrayList<PortfolioDTO>();

        for(PortfolioEntity portfolioEntity : entity)
            entityDTOList.add(this.convertToDTO(portfolioEntity));

        return entityDTOList;
    }

}
