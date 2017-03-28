package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;

/**
 * This interface offers methods to retrieve data from the asset class table in the database.
 *
 * @author Cristian Laurini
 */
@Repository
@Transactional
public interface AssetClassRepository extends PagingAndSortingRepository<AssetClassEntity, Long> {

	/**
	 * This method allows to retrieve all the asset classes.
	 *
	 * @return	List of AssetClassEntities.
	 */
	@Cacheable("assetClassSet")
	public List<AssetClassEntity> findAll();

}