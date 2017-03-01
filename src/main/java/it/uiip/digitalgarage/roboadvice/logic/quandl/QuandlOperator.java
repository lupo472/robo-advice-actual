package it.uiip.digitalgarage.roboadvice.logic.quandl;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBInitializer;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlUpdateScheduler;
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
		for (AssetEntity assetEntity : assets) {
			new QuandlUpdateScheduler().update(assetEntity);
		}
	}
	
	public void initializeFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.daoAsset.findAll();
		for (AssetEntity assetEntity : assets) {
			new QuandlDBInitializer().getData(assetEntity);
		}
	}
	
}
