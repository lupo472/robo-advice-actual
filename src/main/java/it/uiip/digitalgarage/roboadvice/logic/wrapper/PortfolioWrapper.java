package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.logic.converter.AssetClassConverter;
import it.uiip.digitalgarage.roboadvice.logic.converter.AssetConverter;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
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
        List<PortfolioElementDTO> portfolioElementList = new ArrayList<PortfolioElementDTO>();
        dto.setIdUser(entityList.get(0).getUser().getId());
        dto.setDate(entityList.get(0).getDate().toString());
        Map<AssetClassEntity, List<PortfolioEntity>> map = new HashMap<>();
        for(PortfolioEntity entity : entityList){
            if(map.get(entity.getAssetClass()) == null){
                map.put(entity.getAssetClass(), new ArrayList<PortfolioEntity>() );
            }
            map.get(entity.getAssetClass()).add(entity);
        }
        for (AssetClassEntity assetClass : map.keySet()) {
            BigDecimal assetValueSum = new BigDecimal(0);
            PortfolioElementDTO element = new PortfolioElementDTO();
            for(PortfolioEntity portfolio : map.get(assetClass)) {
                assetValueSum.add(portfolio.getValue());
            }
            AssetClassStrategyDTO assetClassStrategyDTO = new AssetClassStrategyDTO();
            assetClassStrategyDTO.setAssetClass(this.assetClassConv.convertToDTO(assetClass));
            //manca la percentuale
            element.setValue(assetValueSum);
            element.setAssetClassStrategy(assetClassStrategyDTO);
            portfolioElementList.add(element);
        }

        dto.setList(portfolioElementList);
        return dto;
    }


}
