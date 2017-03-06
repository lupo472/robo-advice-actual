package it.uiip.digitalgarage.roboadvice.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	
	public UserEntity findByEmail(String email);
	
	public UserEntity findById(Long id);
	
}
