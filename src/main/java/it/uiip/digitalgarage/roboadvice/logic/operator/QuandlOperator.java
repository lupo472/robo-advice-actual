package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBInitializer;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBUpdater;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;

public class QuandlOperator extends GenericOperator {
	
	public QuandlOperator(FinancialDataRepository financialDataRep, AssetRepository assetRep) {
		this.financialDataRep = financialDataRep;
		this.assetRep = assetRep;
	}
	
	public List<FinancialDataEntity> getFinancialDataSet() {
		List<FinancialDataEntity> list =  this.financialDataRep.findAll();
		System.out.println(list.size());
		return list;
	}
	
	public void updateFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		QuandlDBUpdater q = new QuandlDBUpdater();
		for (AssetEntity assetEntity : assets) {
			List<FinancialDataEntity> list = q.getData(assetEntity);
			this.saveList(list);
		}
	}
	
	public void initializeFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		QuandlDBInitializer q = new QuandlDBInitializer();
		for (AssetEntity assetEntity : assets) {
				List<FinancialDataEntity> list = q.getData(assetEntity);
				this.saveList(list);
		}
	}
	
	private void saveList(List<FinancialDataEntity> list) {
		for (FinancialDataEntity financialData : list) {
			if(financialDataRep.findByAssetIdAndDate(financialData.getAsset().getId(), financialData.getDate()).size() == 0) {
				System.out.println(financialData.toString());
				financialDataRep.save(financialData);
			} else {
				System.out.println("Già presente");
			}
		}
	}
	
}
