package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface PortfolioRepository extends PagingAndSortingRepository<PortfolioEntity, Long> {

    public List<PortfolioEntity> findByUserIdAndDate(Long idUser, LocalDate date);

    public List<PortfolioEntity> findByUserIdAndDateBetween(Long idUser, LocalDate finalDate, LocalDate initialDate);

    static final String FIND_LAST_PORTFOLIO_FOR_USER = "SELECT * FROM portfolio "
													 + "WHERE id_user = ?1 "
													 + "AND date = (SELECT max(date) FROM portfolio "
													 + 			  "WHERE id_user = ?1)";

    @Query(value = FIND_LAST_PORTFOLIO_FOR_USER, nativeQuery = true)
    public List<PortfolioEntity> findLastPortfolioForUser(Long idUser);
    
    public PortfolioEntity findByUserIdAndAssetIdAndDate(Long userId, Long assetId, LocalDate date);

    public List<PortfolioEntity> findByUserId(Long userId);
}
