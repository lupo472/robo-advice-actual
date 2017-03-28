package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;

/**
 * This interface offers methods to retrieve data from the default_strategy table in the database.
 *
 * @author Cristian Laurini
 */
@Repository
@Transactional
public interface DefaultStrategyRepository extends PagingAndSortingRepository<DefaultStrategyEntity, Long> {

	public List<DefaultStrategyEntity> findAll();
	
}
