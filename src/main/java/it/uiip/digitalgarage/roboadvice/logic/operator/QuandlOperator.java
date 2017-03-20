package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBInitializer;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBUpdater;

@Service
public class QuandlOperator extends AbstractOperator {

	@CacheEvict(value = "financialDataSet", allEntries = true)
	public void updateFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		//TODO remove counting
		SchedulingOperator.quandl++;
		QuandlDBUpdater q = new QuandlDBUpdater();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> entities = q.getData(asset);
			this.saveList(entities, asset);
		}
	}

	@CacheEvict(value = "financialDataSet", allEntries = true)
	public void initializeFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		QuandlDBInitializer q = new QuandlDBInitializer();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> entities = q.getData(asset);
			this.saveList(entities, asset);
		}
	}

	//TODO prestazioni: la chiamata findByAssetAndDate è necessaria?
	private void saveList(List<FinancialDataEntity> list, AssetEntity asset) {
		for (FinancialDataEntity financialData : list) {
			if(financialDataRep.findByAssetAndDate(asset, financialData.getDate()) == null) {
				//TODO remove counting
				SchedulingOperator.quandl++;
				asset.setLastUpdate(financialData.getDate());
				this.assetRep.save(asset);
				//TODO remove counting
				SchedulingOperator.quandl++;
				financialDataRep.save(financialData);
				//TODO remove counting
				SchedulingOperator.quandl++;
			}
		}
	}
	
}
