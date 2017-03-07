package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PortfolioRepository extends PagingAndSortingRepository<PortfolioEntity, Long> {

	static final String FIND_LAST_PORTFOLIO_FOR_USER = "SELECT * FROM portfolio "
													 + "WHERE id_user = ?1 "
													 + "AND date = (SELECT max(date) FROM portfolio "
													 + 			  "WHERE id_user = ?1)";
	
	@Query(value = FIND_LAST_PORTFOLIO_FOR_USER, nativeQuery = true)
    public List<PortfolioEntity> findLastPortfolioForUser(Long idUser);
	
}
