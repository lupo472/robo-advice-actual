package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class FinancialDataOperator extends AbstractOperator {

	public FinancialDataOperator(FinancialDataRepository financialDataRep) {
		this.financialDataRep = financialDataRep;
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
		return null;
	}
	
	public List<FinancialDataDTO> getFinancialDataSetForAsset(DataRequestDTO request) {
		Calendar calendar = Calendar.getInstance();
		List<FinancialDataEntity> list;
		if(request.getPeriod() == 0) {
			list = this.financialDataRep.findByAssetId(request.getIdAsset());
		} else {
			calendar.add(Calendar.DATE, -(request.getPeriod()));
			LocalDate date = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));		
			list =  this.financialDataRep.findByAssetIdForPeriod(request.getIdAsset(), date.toString());
		}
		return this.financialDataConv.convertToDTO(list);
	}
	
}
