package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataElementDTO;

@Service
public class FinancialDataOperator extends AbstractOperator {

	/*
	* This method is not used.
	* TODO: Improve performance if necessary
	*/
	@Cacheable("financialDataSet")
	public List<FinancialDataDTO> getFinancialDataSet(int period) {
		List<FinancialDataDTO> result = new ArrayList<>();
		List<AssetClassEntity> assetClassSet = this.assetClassRep.findAll();
		for (AssetClassEntity assetClass : assetClassSet) {
			List<FinancialDataElementDTO> list = this.getFinancialDataSetForAssetClass(assetClass, period);
			FinancialDataDTO financialData = new FinancialDataDTO();
			financialData.setAssetClass(this.assetClassConv.convertToDTO(assetClass));
			financialData.setList(list);
			result.add(financialData);
		}
		Collections.sort(result);
		return result;
	}
	
	private List<FinancialDataElementDTO> getFinancialDataSetForAssetClass(AssetClassEntity assetClass, int period) {
		List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
		Map<String, BigDecimal> map = createMap(period, assets);
		List<FinancialDataElementDTO> result = computeResult(map);
		return result;
	}

	private Map<String, BigDecimal> createMap(int period, List<AssetEntity> assets) {
		Map<String, BigDecimal> map = new HashMap<>();
		boolean interrupt = true;
		if(period == 0) {
			interrupt = false;
		}
		for (AssetEntity asset : assets) {
			int n = 0;
			LocalDate entityDate = LocalDate.now();
			BigDecimal entityValue = new BigDecimal(0);
			boolean first = true;
			while(true) {
				if(interrupt && n >= period) {
					break;
				}
				LocalDate date = LocalDate.now().minus(Period.ofDays(n));
				if(first || date.isBefore(entityDate)) {
					FinancialDataEntity entity = this.financialDataRep.findTopByAssetAndDateLessThanEqualOrderByDateDesc(asset, date);
					first = false;
					if(entity == null) {
						break;
					}
					entityDate = entity.getDate();
					entityValue = entity.getValue();
				}
				if(map.get(date.toString()) == null) {
					map.put(date.toString(), new BigDecimal(0));
				}
				map.put(date.toString(), map.get(date.toString()).add(entityValue));
				n++;
			}
		}
		return map;
	}

	private List<FinancialDataElementDTO> computeResult(Map<String, BigDecimal> map) {
		List<FinancialDataElementDTO> result = new ArrayList<>();
		for (String date : map.keySet()) {
			FinancialDataElementDTO element = new FinancialDataElementDTO();
			element.setDate(date);
			element.setValue(map.get(date));
			result.add(element);
		}
		Collections.sort(result);
		return result;
	}	
	
}
