package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataElementDTO;

/**
 * This class manages all the computations related to FinancialData.
 *
 * @author Cristian Laurini
 */
@Service
public class FinancialDataOperator extends AbstractOperator {

	/**
	 * This method returns all the financial data for each asset class in the selected period.
	 *
	 * @param period	PeriodDTO is the number of days to retrieve.
	 * @return			List of FinancialDataDTOs or null if some problem occurs.
	 */
	@Cacheable("financialDataSet")
	public List<FinancialDataDTO> getFinancialDataSet(int period) {
		List<FinancialDataDTO> result = new ArrayList<>();
		List<AssetClassEntity> assetClassList = this.assetClassRep.findAll();
		for (AssetClassEntity assetClass : assetClassList) {
			List<FinancialDataElementDTO> elementList = this.getFinancialDataSetForAssetClass(assetClass, period);
			if(elementList == null) {
				return null;
			}
			FinancialDataDTO financialData = new FinancialDataDTO();
			financialData.setAssetClass(this.assetClassConv.convertToDTO(assetClass));
			financialData.setList(elementList);
			result.add(financialData);
		}
		Collections.sort(result);
		return result;
	}
	
	private List<FinancialDataElementDTO> getFinancialDataSetForAssetClass(AssetClassEntity assetClass, int period) {
		List<AssetEntity> assets = this.assetRep.findByAssetClass(assetClass);
		Map<String, BigDecimal> assetClassValuePerDateMap = createMap(period, assets);
		if(assetClassValuePerDateMap == null) {
			return null;
		}
		List<FinancialDataElementDTO> result = computeResult(assetClassValuePerDateMap);
		return result;
	}

	private Map<String, BigDecimal> createMap(int period, List<AssetEntity> assets) {
		Map<String, BigDecimal> map = new HashMap<>();
		boolean interrupt = true;
		if(period == 0) {
			interrupt = false;
		}
		LocalDate startDate = null;
		if(period != 0) {
			startDate = LocalDate.now().minus(Period.ofDays(period));
		}
		List<FinancialDataEntity> financialDataList;
		for (AssetEntity asset : assets) {
			if(period != 0) {
				financialDataList = this.financialDataRep.findByAssetAndDateGreaterThanOrderByDateDesc(asset, startDate);
			} else {
				financialDataList = this.financialDataRep.findByAsset(asset);
			}
			Collections.sort(financialDataList);
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
					FinancialDataEntity financialData = null;
					if(financialDataList.size() == 0) {
						if(interrupt) {
							financialData = this.financialDataRep.findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(asset, date);
						} else {
							break;
						}
					} else {
						financialData = financialDataList.get(financialDataList.size() - 1);
						financialDataList.remove(financialData);
					}
					first = false;
					if(financialData == null) {
						return null;
					}
					entityDate = financialData.getDate();
					entityValue = financialData.getValue();
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

	private List<FinancialDataElementDTO> computeResult(Map<String, BigDecimal> assetClassValuePerDateMap) {
		List<FinancialDataElementDTO> result = new ArrayList<>();
		for (String date : assetClassValuePerDateMap.keySet()) {
			FinancialDataElementDTO element = new FinancialDataElementDTO();
			element.setDate(date);
			element.setValue(assetClassValuePerDateMap.get(date));
			result.add(element);
		}
		Collections.sort(result);
		return result;
	}	
	
}
