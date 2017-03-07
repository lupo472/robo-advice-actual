package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBInitializer;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBUpdater;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class QuandlOperator extends AbstractOperator {
	
	public QuandlOperator(FinancialDataRepository financialDataRep, AssetRepository assetRep) {
		this.financialDataRep = financialDataRep;
		this.assetRep = assetRep;
	}
	
	public void updateFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		QuandlDBUpdater q = new QuandlDBUpdater();
		for (AssetEntity asset : assets) {
			List<FinancialDataDTO> list = q.getData(asset);
			List<FinancialDataEntity> entities = this.financialDataConv.convertToEntity(list);
			this.saveList(entities);
		}
	}
	
	public void initializeFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		QuandlDBInitializer q = new QuandlDBInitializer();
		for (AssetEntity assetEntity : assets) {
				List<FinancialDataDTO> list = q.getData(assetEntity);
				List<FinancialDataEntity> entities = this.financialDataConv.convertToEntity(list);
				this.saveList(entities);
		}
	}
	
	private void saveList(List<FinancialDataEntity> list) {
		for (FinancialDataEntity financialData : list) {
			if(financialDataRep.findByAssetIdAndDate(financialData.getAsset().getId(), financialData.getDate()).size() == 0) {
				financialDataRep.save(financialData);
			}
		}
	}
	
}
