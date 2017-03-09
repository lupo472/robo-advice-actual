package it.uiip.digitalgarage.roboadvice.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AuthEntity;

@Repository
@Transactional
public interface AuthRepository extends PagingAndSortingRepository<AuthEntity, Long> {

	
	
}
