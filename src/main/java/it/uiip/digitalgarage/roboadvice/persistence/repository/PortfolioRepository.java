package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface PortfolioRepository extends PagingAndSortingRepository<PortfolioEntity, Long> {
    
	public List<PortfolioEntity> findByUser(UserEntity user);
    
    public List<PortfolioEntity> findByUserAndDate(UserEntity user, LocalDate date);
	
	public List<PortfolioEntity> findByUserAndDateBetween(UserEntity user, LocalDate finalDate, LocalDate initialDate);
  
	public PortfolioEntity findByUserAndAssetAndDate(UserEntity user, AssetEntity asset, LocalDate date);
    
}
