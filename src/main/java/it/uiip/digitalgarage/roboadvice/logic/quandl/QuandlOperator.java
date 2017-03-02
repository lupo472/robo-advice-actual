package it.uiip.digitalgarage.roboadvice.logic.quandl;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.logic.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBInitializer;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBUpdater;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;

public class QuandlOperator {
	
	private FinancialDataRepository daoFinancialData;
	private AssetRepository daoAsset; 
	
	public QuandlOperator(FinancialDataRepository daoFinancialData, AssetRepository daoAsset) {
		this.daoFinancialData = daoFinancialData;
		this.daoAsset = daoAsset;
	}
	
	public void updateFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.daoAsset.findAll();
		QuandlDBUpdater q = new QuandlDBUpdater();
		for (AssetEntity assetEntity : assets) {
			List<FinancialDataEntity> list = q.getData(assetEntity);
			this.saveList(list);
		}
	}
	
	public void initializeFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.daoAsset.findAll();
		QuandlDBInitializer q = new QuandlDBInitializer();
		for (AssetEntity assetEntity : assets) {
				List<FinancialDataEntity> list = q.getData(assetEntity);
				this.saveList(list);
		}
	}
	
	private void saveList(List<FinancialDataEntity> list) {
		for (FinancialDataEntity financialData : list) {
			if(daoFinancialData.findByAssetIdAndDate(financialData.getAsset().getId(), financialData.getDate()).size() == 0) {
				System.out.println(financialData.toString());
				daoFinancialData.save(financialData);
			} else {
				System.out.println("Già presente");
			}
		}
	}
	
}
