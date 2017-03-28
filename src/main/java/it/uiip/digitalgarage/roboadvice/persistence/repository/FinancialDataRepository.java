package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;

/**
 * This interface offers methods to retrieve data from the financial_data table in the database.
 *
 * @author Cristian Laurini
 */
@Repository
@Transactional
public interface FinancialDataRepository extends PagingAndSortingRepository<FinancialDataEntity, Long> {

	/**
	 * This method allows to retrieve all the financial data for the selected asset.
	 *
	 * @param asset		AssetEntity is the asset for which you want to retrieve the data.
	 * @return			List of FinancialDataEntities.
	 */
	public List<FinancialDataEntity> findByAsset(AssetEntity asset);

	/**
	 * This method allows to retrieve a financial data for the selected asset in a specific date.
	 *
	 * @param asset		AssetEntity is the asset for which you want to retrieve the data.
	 * @param date		LocalDate is the date for which you want to retrieve data.
	 * @return			FinancialDataEntity.
	 */
	public FinancialDataEntity findByAssetAndDate(AssetEntity asset, LocalDate date);

	/**
	 * This method allows to retrieve all the financial data for the selected asset after a specific date.
	 *
	 * @param asset		AssetEntity is the asset for which you want to retrieve the data.
	 * @param date		LocalDate is the date starting from which you want to retrieve data.
	 * @return			List of FinancialDataEntities.
	 */
	public List<FinancialDataEntity> findByAssetAndDateGreaterThanOrderByDateDesc(AssetEntity asset, LocalDate date);

	/**
	 * This method allows to retrieve first financial data of ever for the selected asset.
	 *
	 * @param asset		AssetEntity is the asset for which you want to retrieve the data.
	 * @return			FinancialDataEntity.
	 */
	public FinancialDataEntity findTop1ByAssetOrderByDateAsc(AssetEntity asset);

	/**
	 * This method allows to retrieve the first financial data for the selected asset before a specific date.
	 *
	 * @param asset		AssetEntity is the asset for which you want to retrieve the data.
	 * @param date		LocalDate is the date before which you want to retrieve data.
	 * @return			FinancialDataEntity.
	 */
	public FinancialDataEntity findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(AssetEntity asset, LocalDate date);

	/**
	 * This method allows to retrieve all the financial data for the selected asset before a specific date.
	 *
	 * @param asset		AssetEntity is the asset for which you want to retrieve the data.
	 * @param date		LocalDate is the date before which you want to retrieve data.
	 * @return			List of FinancialDataEntities
	 */
	public List<FinancialDataEntity> findByAssetAndDateLessThanOrderByDateAsc(AssetEntity asset, LocalDate date);

}
