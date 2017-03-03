package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;

@Repository
@Transactional
public interface FinancialDataRepository extends PagingAndSortingRepository<FinancialDataEntity, Long> {

	public List<FinancialDataEntity> findAll();
	
	@Query(value = "SELECT * FROM financial_data WHERE id_asset = ?1", nativeQuery = true)
	public List<FinancialDataEntity> boh(Long assetId);
	
	public List<FinancialDataEntity> findByAssetIdAndDate(Long assetId, LocalDate date);
	
}
