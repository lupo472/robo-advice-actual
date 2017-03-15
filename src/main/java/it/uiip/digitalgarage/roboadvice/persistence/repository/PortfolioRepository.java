package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.model.AssetClassValue;
import it.uiip.digitalgarage.roboadvice.persistence.model.TotalValue;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface PortfolioRepository extends PagingAndSortingRepository<PortfolioEntity, Long> {
    
	static final String SUM_VALUES = "SELECT NEW it.uiip.digitalgarage.roboadvice.persistence.model.TotalValue"
										+ "(SUM(p.value)) FROM PortfolioEntity p "
										+ "WHERE p.user = ?1 AND p.date = ?2";
	
	static final String SUM_VALUES_ASSET_CLASS = "SELECT NEW it.uiip.digitalgarage.roboadvice.persistence.model.AssetClassValue"
												+ "(p.assetClass, SUM(p.value)) FROM PortfolioEntity p "
												+ "WHERE p.user = ?2 AND p.date = ?3 AND p.assetClass = ?1";
	
	@Query(value = SUM_VALUES)
	public TotalValue sumValues(UserEntity user, LocalDate date); 
	
	@Query(value = SUM_VALUES_ASSET_CLASS)
	public AssetClassValue sumValuesForAssetClass(AssetClassEntity assetClass, UserEntity user, LocalDate date);
	
	public List<PortfolioEntity> findByUser(UserEntity user);
    
    public List<PortfolioEntity> findByUserAndDate(UserEntity user, LocalDate date);
	
	public List<PortfolioEntity> findByUserAndDateBetween(UserEntity user, LocalDate finalDate, LocalDate initialDate);
  
	public PortfolioEntity findByUserAndAssetAndDate(UserEntity user, AssetEntity asset, LocalDate date);
    
}
