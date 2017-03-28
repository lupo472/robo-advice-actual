package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBManager;

@Service
public class QuandlOperator extends AbstractOperator {

	private QuandlDBManager quandlDBManager = new QuandlDBManager();

	@CacheEvict(value = {"activeStrategy", "strategies", "currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public void updateFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> entities = this.quandlDBManager.getDataUpdate(asset);
			this.saveList(entities, asset);
		}
	}

	@CacheEvict(value = {"activeStrategy", "strategies", "currentPortfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public void initializeFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> entities = this.quandlDBManager.getDataInitialization(asset);
			this.saveList(entities, asset);
		}
	}

	private void saveList(List<FinancialDataEntity> list, AssetEntity asset) {
		for (FinancialDataEntity financialData : list) {
			if(financialDataRep.findByAssetAndDate(asset, financialData.getDate()) == null) {
				asset.setLastUpdate(financialData.getDate());
				this.assetRep.save(asset);
				financialDataRep.save(financialData);
			}
		}
	}
	
}
