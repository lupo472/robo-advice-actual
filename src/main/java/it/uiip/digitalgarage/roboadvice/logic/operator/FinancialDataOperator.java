package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.DataForAssetRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class FinancialDataOperator extends AbstractOperator {

	public FinancialDataOperator(FinancialDataRepository financialDataRep) {
		this.financialDataRep = financialDataRep;
	}
	
	public List<FinancialDataDTO> getFinancialDataSet() {
		List<FinancialDataEntity> list =  this.financialDataRep.findAll();
		return this.financialDataConv.convertToDTO(list);
	}
	
	public List<FinancialDataDTO> getFinancialDataSet(DataForAssetRequestDTO request) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -(request.getPeriod()));
		LocalDate date = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));		
		System.out.println("++++++++ " + date.toString());
		List<FinancialDataEntity> list =  this.financialDataRep.findByAssetForPeriod(request.getIdAsset(), date.toString());
		return this.financialDataConv.convertToDTO(list);
	}
	
}
