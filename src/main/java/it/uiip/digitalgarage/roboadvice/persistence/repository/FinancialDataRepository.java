package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;

@Repository
@Transactional
public interface FinancialDataRepository extends PagingAndSortingRepository<FinancialDataEntity, Long> {

	public List<FinancialDataEntity> findByAsset(AssetEntity asset);

	public FinancialDataEntity findByAssetAndDate(AssetEntity asset, LocalDate date);

	public List<FinancialDataEntity> findByAssetAndDateGreaterThanOrderByDateDesc(AssetEntity asset, LocalDate date);

	public FinancialDataEntity findTop1ByAssetAndDateLessThanEqualOrderByDateDesc(AssetEntity asset, LocalDate date);

	public FinancialDataEntity findTop1ByAssetOrderByDateAsc(AssetEntity asset);

	public List<FinancialDataEntity> findByAssetAndDateLessThanOrderByDateAsc(AssetEntity asset, LocalDate date);

}
