package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface PortfolioRepository extends PagingAndSortingRepository<PortfolioEntity, Long> {
    //TODO Deprecated query
    //public List<PortfolioEntity> findByUserIdAndDate(Long idUser, LocalDate date);

    public List<PortfolioEntity> findByUserAndDateBetween(UserEntity user, LocalDate finalDate, LocalDate initialDate);

    //TODO delete
    static final String FIND_LAST_PORTFOLIO_FOR_USER_ID = "SELECT * FROM portfolio "
													 + "WHERE id_user = ?1 "
													 + "AND date = (SELECT max(date) FROM portfolio "
													 + 			   "WHERE id_user = ?1)";
    
    static final String FIND_LAST_PORTFOLIO_FOR_USER = "SELECT p FROM PortfolioEntity p "
			 										 + "WHERE p.user = ?1 "
			 										 + "AND p.date = ?2";
    
    static final String FIND_BY_USERID_ASSETID_AND_DATE = "SELECT * FROM portfolio "
    													+ "WHERE id_user = ?1 AND id_asset = ?2 "
    													+ "AND date = ?3";
    //TODO delete
    @Query(value = FIND_LAST_PORTFOLIO_FOR_USER_ID, nativeQuery = true)
    public List<PortfolioEntity> findLastPortfolioForUser(Long idUser);
    
    //TODO new version
    @Query(value = FIND_LAST_PORTFOLIO_FOR_USER)
    public List<PortfolioEntity> findLastPortfolioForUser(UserEntity user, LocalDate lastUpdate);
    
    @Query(value = FIND_BY_USERID_ASSETID_AND_DATE, nativeQuery = true)
    public PortfolioEntity findByUserIdAndAssetIdAndDate(Long userId, Long assetId, String date);

    public List<PortfolioEntity> findByUser(UserEntity user);
}
