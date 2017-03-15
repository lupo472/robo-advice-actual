package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;

@Repository
@Transactional
public interface FinancialDataRepository extends PagingAndSortingRepository<FinancialDataEntity, Long> {

//	static final String FIND_LAST_FOR_ASSET = "SELECT * FROM financial_data "
//											+ "WHERE id_asset = ?1 "
//											+ "AND date = (SELECT max(date) FROM financial_data "
//											+ 			  "WHERE id_asset = ?1)";
//	
//	static final String FIND_LAST_FOR_ASSET_BEFORE =  "SELECT * FROM financial_data "
//													+ "WHERE id_asset = ?1 "
//													+ "AND date = (SELECT max(date) FROM financial_data "
//													+ 			  "WHERE id_asset = ?1 "
//													+ 			  "AND date <= ?2)";
	
//	static final String FIND_BY_ASSET_ID = "SELECT * FROM financial_data WHERE id_asset = ?1 ORDER BY date ASC";
	
//	static final String FIND_BY_ASSET_ID_FOR_PERIOD = "SELECT * FROM financial_data WHERE id_asset = ?1 "
//													+ "AND date BETWEEN ?2 AND NOW() ORDER BY date ASC";
	
//	public List<FinancialDataEntity> findByAssetAndDate(AssetEntity asset, LocalDate date);
	
//	public List<FinancialDataEntity> findAll();
	
//	@Query(value = FIND_LAST_FOR_ASSET, nativeQuery = true)
	public FinancialDataEntity findByAssetAndDate(AssetEntity asset, LocalDate date);
	
//	@Query(value = FIND_LAST_FOR_ASSET_BEFORE, nativeQuery = true)
//	public FinancialDataEntity findLastForAnAssetBefore(Long assetId, String date);
	
//	@Query(value = FIND_BY_ASSET_ID, nativeQuery = true)
//	public List<FinancialDataEntity> findByAssetId(Long assetId);
	
//	@Query(value = FIND_BY_ASSET_ID_FOR_PERIOD, nativeQuery = true)
//	public List<FinancialDataEntity> findByAssetIdForPeriod(Long assetId, String date);
	
}
