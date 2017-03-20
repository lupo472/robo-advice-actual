package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;

@Repository
@Transactional
public interface AssetClassRepository extends PagingAndSortingRepository<AssetClassEntity, Long> {

	@Cacheable("assetClassSet")
	public List<AssetClassEntity> findAll();

}