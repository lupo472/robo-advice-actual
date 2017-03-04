package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class FinancialDataOperator extends AbstractOperator {

	public FinancialDataOperator(FinancialDataRepository financialDataRep) {
		this.financialDataRep = financialDataRep;
	}
	
	public FinancialDataOperator(FinancialDataRepository financialDataRep, AssetRepository assetRep) {
		this.financialDataRep = financialDataRep;
		this.assetRep = assetRep;
	}
	
	public List<FinancialDataDTO> getFinancialDataSet() {
		List<FinancialDataEntity> list =  this.financialDataRep.findAll();
		return this.financialDataConv.convertToDTO(list);
	}
	
	public FinancialDataDTO findLast(AssetDTO asset) {
		FinancialDataEntity entity = this.financialDataRep.findLastForAnAsset(asset.getId());
		return this.financialDataConv.convertToDTO(entity);
	}
	
	public List<FinancialDataDTO> getFinancialDataSetForAssetClass(DataRequestDTO request) {
		List<AssetEntity> assets = this.assetRep.findByAssetClassId(request.getId());
		List<FinancialDataEntity> financialDataSet = null;
		for (AssetEntity assetEntity : assets) {
			request.setId(assetEntity.getId());
			financialDataSet = this.financialDataConv.convertToEntity(this.getFinancialDataSetForAsset(request));
			System.out.println(assetEntity.getName() + ": " + financialDataSet.size());
		}
		return null;
	}
	
	public List<FinancialDataDTO> getFinancialDataSetForAsset(DataRequestDTO request) {
		Calendar calendar = Calendar.getInstance();
		List<FinancialDataEntity> list;
		if(request.getPeriod() == 0) {
			list = this.financialDataRep.findByAssetId(request.getId());
		} else {
			calendar.add(Calendar.DATE, -(request.getPeriod()));
			LocalDate date = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));		
			list =  this.financialDataRep.findByAssetIdForPeriod(request.getId(), date.toString());
		}
		return this.financialDataConv.convertToDTO(list);
	}
	
}
