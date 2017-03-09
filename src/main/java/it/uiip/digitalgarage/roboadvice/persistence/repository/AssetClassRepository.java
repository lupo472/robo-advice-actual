package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;

@Repository
@Transactional
public interface AssetClassRepository extends PagingAndSortingRepository<AssetClassEntity, Long> {

	//@Query(value = "SELECT * FROM asset_class ORDER BY id", nativeQuery = true)
	public List<AssetClassEntity> findAllOrderById();

	public AssetClassEntity findById(Long id);
		
}