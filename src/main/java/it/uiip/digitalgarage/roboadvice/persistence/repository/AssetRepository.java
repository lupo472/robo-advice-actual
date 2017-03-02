package it.uiip.digitalgarage.roboadvice.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.logic.entity.AssetEntity;

@Repository
@Transactional
public interface AssetRepository extends PagingAndSortingRepository<AssetEntity, Long>{
	
	public AssetEntity findById(Long id);

}
