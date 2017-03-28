package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;

/**
 * This interface offers methods to retrieve data from the default strategy table in the database.
 *
 * @author Cristian Laurini
 */
@Repository
@Transactional
public interface DefaultStrategyRepository extends PagingAndSortingRepository<DefaultStrategyEntity, Long> {

	/**
	 * This method allows to retrieve all the default strategies.
	 *
	 * @return	List of DefaultStrategyEntities.
	 */
	public List<DefaultStrategyEntity> findAll();
	
}
