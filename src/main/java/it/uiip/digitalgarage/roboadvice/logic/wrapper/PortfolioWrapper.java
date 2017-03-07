package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.logic.converter.AssetClassConverter;
import it.uiip.digitalgarage.roboadvice.logic.converter.AssetConverter;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PortfolioWrapper implements GenericWrapper<PortfolioEntity, PortfolioDTO> {

    private AssetConverter assetConv = new AssetConverter();
    private AssetClassConverter assetClassConv = new AssetClassConverter();

    @Override
    public List<PortfolioEntity> unwrapToEntity(PortfolioDTO dto) {
        List<PortfolioEntity> portfolioEntityList = new ArrayList<PortfolioEntity>();
        for(PortfolioElementDTO element : dto.getList()){
            PortfolioEntity entity = new PortfolioEntity();
            UserEntity user = new UserEntity();
            user.setId(dto.getIdUser());
            entity.setUser(user);
            entity.setAsset(this.assetConv.convertToEntity(element.getAsset()));
            entity.setAssetClass(this.assetClassConv.convertToEntity(element.getAsset().getAssetClass()));
            entity.setUnits(element.getUnits());
            entity.setValue(element.getValue());
            entity.setDate(LocalDate.parse(dto.getDate()));
            portfolioEntityList.add(entity);
        }
        return portfolioEntityList;
    }

    @Override
    public PortfolioDTO wrapToDTO(List<PortfolioEntity> entityList) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setList(new ArrayList<>());
        dto.setIdUser(entityList.get(0).getId());
        dto.setDate(entityList.get(0).getDate().toString());
        for (PortfolioEntity entity : entityList) {
            PortfolioElementDTO element = new PortfolioElementDTO();
            element.setAsset(this.assetConv.convertToDTO(entity.getAsset()));
            element.setUnits(entity.getUnits());
            element.setValue(entity.getValue());
            dto.getList().add(element);
        }
        return dto;
    }

}
