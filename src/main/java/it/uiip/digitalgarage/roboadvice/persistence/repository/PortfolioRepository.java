package it.uiip.digitalgarage.roboadvice.persistence.repository;


import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Luca on 02/03/2017.
 */
public interface PortfolioRepository extends PagingAndSortingRepository<PortfolioEntity,Long> {
}
