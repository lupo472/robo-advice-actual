package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

/**
 * This class manages all the computations related to AssetClass.
 *
 * @author Cristian Laurini
 */
@Service
public class AssetClassOperator extends AbstractOperator {

	/**
	 * This method returns all the assetClasses in the database.
	 *
	 * @return	List of AssetClassDTOs.
	 */
	public List<AssetClassDTO> getAssetClassSet() {
		List<AssetClassEntity> list = this.assetClassRep.findAll();
		return this.assetClassConv.convertToDTO(list);
	}

	/**
	 * This method computes the minimum date for which the selected assetClass has financialData.
	 *
	 * @param assetClass	AssetClassEntity is the asset class for which compute the date.
	 * @return				LocalDate.
	 */
	public LocalDate getMinDate(AssetClassEntity assetClass) {
		List<AssetEntity> list = this.assetRep.findByAssetClass(assetClass);
		LocalDate date = null;
		for(AssetEntity asset : list) {
			FinancialDataEntity financialData = this.financialDataRep.findTop1ByAssetOrderByDateAsc(asset);
			if(date == null || date.isBefore(financialData.getDate())){
				date = financialData.getDate();
			}
		}
		return date;
	}

}
