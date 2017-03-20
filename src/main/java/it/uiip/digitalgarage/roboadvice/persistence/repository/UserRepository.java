package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	@Cacheable("user")
	public UserEntity findByEmail(String email);

	public List<UserEntity> findAll();

	@CacheEvict(value = "user", allEntries = true)
	@Override
	public UserEntity save(UserEntity user);
	
}
