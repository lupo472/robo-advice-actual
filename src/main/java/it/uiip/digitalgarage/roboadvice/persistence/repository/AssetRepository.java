package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;

/**
 * This interface offers methods to retrieve data from the asset table in the database.
 *
 * @author Cristian Laurini
 */
@Repository
@Transactional
public interface AssetRepository extends PagingAndSortingRepository<AssetEntity, Long> {

	/**
	 * This method allows to retrieve all the assets.
	 *
	 * @return	List of AssetEntities.
	 */
	public List<AssetEntity> findAll();

	/**
	 * This method allows to retrieve all the assets for the selected asset class.
	 *
	 * @param assetClass	AssetClassEntity is the asset class for which you want to retrieve the assets.
	 * @return				List of AssetEntities.
	 */
	public List<AssetEntity> findByAssetClass(AssetClassEntity assetClass);

}
