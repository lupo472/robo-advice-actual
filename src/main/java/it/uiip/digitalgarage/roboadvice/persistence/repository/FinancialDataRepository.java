package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;

@Repository
@Transactional
public interface FinancialDataRepository extends PagingAndSortingRepository<FinancialDataEntity, Long> {

	public FinancialDataEntity findByAssetAndDate(AssetEntity asset, LocalDate date);
	
}
