package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class FinancialDataOperator extends AbstractOperator {

	public FinancialDataOperator(FinancialDataRepository financialDataRep) {
		this.financialDataRep = financialDataRep;
	}
	
	public FinancialDataOperator(FinancialDataRepository financialDataRep, AssetRepository assetRep, AssetClassRepository assetClassRep) {
		this.financialDataRep = financialDataRep;
		this.assetRep = assetRep;
		this.assetClassRep = assetClassRep;
	}
	
	public List<FinancialDataDTO> getFinancialDataSet() {
		List<FinancialDataEntity> list =  this.financialDataRep.findAll();
		return this.financialDataConv.convertToDTO(list);
	}
	
	public FinancialDataDTO findLast(DataRequestDTO request) {
		FinancialDataEntity entity = null;
		if(request.getPeriod() == 0) {
			entity = this.financialDataRep.findLastForAnAsset(request.getId());
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -(request.getPeriod()));
			LocalDate date = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
			entity = this.financialDataRep.findLastForAnAssetBefore(request.getId(), date.toString());
		}
		if(entity == null) {
			return null;
		}
		return this.financialDataConv.convertToDTO(entity);
	}
	
	//TODO Manage the case when period = 0
	//TODO improve scalability
	public List<FinancialDataClassDTO> getFinancialDataSetForAssetClass(DataRequestDTO request) {
		List<AssetEntity> assets = this.assetRep.findByAssetClassId(request.getId());
		
		Map<String, BigDecimal> map = new HashMap<>();
		
		if(request.getPeriod() == 0) {
			for(AssetEntity assetEntity : assets) {
				FinancialDataEntity entity = null;
				int n = 1;
				while(true) {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -n);
					LocalDate date = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
					entity = this.financialDataRep.findLastForAnAssetBefore(assetEntity.getId(), date.toString());
					if(entity == null) {
						break;
					}
					if(map.get(date.toString()) == null) {
						map.put(date.toString(), new BigDecimal(0));
					}
					map.put(date.toString(), map.get(date.toString()).add(entity.getValue()));
					n++;
				}
			}
			List<FinancialDataClassDTO> result = new ArrayList<>();
			for (String date : map.keySet()) {
				FinancialDataClassDTO f = new FinancialDataClassDTO();
				f.setAssetClass(this.assetClassConv.convertToDTO(this.assetClassRep.findById(request.getId())));
				f.setDate(date);
				f.setValue(map.get(date));
				result.add(f);
			}
			return result;
		}
		
		
		int days = request.getPeriod();
		while(days > 0) {
			for (AssetEntity assetEntity : assets) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -days);
				LocalDate date = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
				FinancialDataEntity entity = this.financialDataRep.findLastForAnAssetBefore(assetEntity.getId(), date.toString());
				
				if(map.get(date.toString()) == null) {
					map.put(date.toString(), new BigDecimal(0));
				}
				map.put(date.toString(), map.get(date.toString()).add(entity.getValue()));
			}
			days--;
		}
		List<FinancialDataClassDTO> result = new ArrayList<>();
		for (String date : map.keySet()) {
			FinancialDataClassDTO f = new FinancialDataClassDTO();
			f.setAssetClass(this.assetClassConv.convertToDTO(this.assetClassRep.findById(request.getId())));
			f.setDate(date);
			f.setValue(map.get(date));
			result.add(f);
		}
		
//		List<FinancialDataEntity> financialDataSet = null;
//		for (AssetEntity assetEntity : assets) {
//			request.setId(assetEntity.getId());
//			financialDataSet = this.financialDataConv.convertToEntity(this.getFinancialDataSetForAsset(request));
//			System.out.println(assetEntity.getName() + ": " + financialDataSet.size());
//		}
		return result;
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
