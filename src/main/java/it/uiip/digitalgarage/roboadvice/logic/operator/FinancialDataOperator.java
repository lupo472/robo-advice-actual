package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.FinancialDataRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.DataRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataClassDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

@Service
public class FinancialDataOperator extends AbstractOperator {

//	public FinancialDataOperator(FinancialDataRepository financialDataRep) {
//		this.financialDataRep = financialDataRep;
//	}
	
//	public FinancialDataOperator(FinancialDataRepository financialDataRep, AssetRepository assetRep, AssetClassRepository assetClassRep) {
//		this.financialDataRep = financialDataRep;
//		this.assetRep = assetRep;
//		this.assetClassRep = assetClassRep;
//	}
	
	public List<FinancialDataDTO> getFinancialDataSet() {
		List<FinancialDataEntity> list =  this.financialDataRep.findAll();
		return this.financialDataConv.convertToDTO(list);
	}
	
	public FinancialDataDTO findLast(DataRequestDTO request) {
		FinancialDataEntity entity = null;
		if(request.getPeriod() == 0) {
			entity = this.financialDataRep.findLastForAnAsset(request.getId());
		} else {
			LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod()));
			entity = this.financialDataRep.findLastForAnAssetBefore(request.getId(), date.toString());
		}
		if(entity == null) {
			return null;
		}
		return this.financialDataConv.convertToDTO(entity);
	}
	
	//TODO improve scalability
	public List<FinancialDataClassDTO> getFinancialDataSetForAssetClass(DataRequestDTO request) {
		List<AssetEntity> assets = this.assetRep.findByAssetClassId(request.getId());
		
		Map<String, BigDecimal> map = new HashMap<>();
		
		if(request.getPeriod() == 0) {
			for(AssetEntity assetEntity : assets) {
				FinancialDataEntity entity = null;
				int n = 1;
				while(true) {
					LocalDate date = LocalDate.now().minus(Period.ofDays(n));
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
			Collections.sort(result);
			return result;
		}
		
		
		int days = request.getPeriod();
		while(days > 0) {
			for (AssetEntity assetEntity : assets) {
				LocalDate date = LocalDate.now().minus(Period.ofDays(days));
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
		Collections.sort(result);
		return result;
	}
	
	public List<FinancialDataDTO> getFinancialDataSetForAsset(DataRequestDTO request) {
		List<FinancialDataEntity> list;
		if(request.getPeriod() == 0) {
			list = this.financialDataRep.findByAssetId(request.getId());
		} else {
			LocalDate date = LocalDate.now().minus(Period.ofDays(request.getPeriod()));
			list =  this.financialDataRep.findByAssetIdForPeriod(request.getId(), date.toString());
		}
		return this.financialDataConv.convertToDTO(list);
	}
	
}
