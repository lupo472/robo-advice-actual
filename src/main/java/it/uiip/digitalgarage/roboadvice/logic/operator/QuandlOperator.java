package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.quandl.QuandlDBManager;

/**
 * This class contains methods to manage financial data from quandl.
 *
 * @author Cristian Laurini
 */
@Service
public class QuandlOperator extends AbstractOperator {

	private QuandlDBManager quandlDBManager = new QuandlDBManager();

	/**
	 * This method makes the update of the financial data on the database.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public void updateFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> financialDataList = this.quandlDBManager.getDataUpdate(asset);
			this.saveList(financialDataList, asset);
		}
	}

	/**
	 * This method makes the inizialization of the financial data on the database.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public void initializeFinancialDataSet() {
		List<AssetEntity> assets = (List<AssetEntity>) this.assetRep.findAll();
		for (AssetEntity asset : assets) {
			List<FinancialDataEntity> financialDataList = this.quandlDBManager.getDataInitialization(asset);
			this.saveList(financialDataList, asset);
		}
	}

	private void saveList(List<FinancialDataEntity> financialDataList, AssetEntity asset) {
		for (FinancialDataEntity financialData : financialDataList) {
			if(financialDataRep.findByAssetAndDate(asset, financialData.getDate()) == null) {
				asset.setLastUpdate(financialData.getDate());
				this.assetRep.save(asset);
				financialDataRep.save(financialData);
			}
		}
	}
	
}
