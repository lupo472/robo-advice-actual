package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

/**
 * This interface offers methods to retrieve data from the user table in the database.
 *
 * @author Cristian Laurini
 */
@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	/**
	 * This method allows to retrieve an user from the database for the specified email.
	 *
	 * @param email		String that represents the email of the user to retrieve.
	 * @return			UserEntity that represents the retrieved user.
	 */
	@Cacheable("user")
	public UserEntity findByEmail(String email);

	/**
	 * This method allows to retrieve all the users from the database.
	 *
	 * @return		List of UserEntity containing all the users.
	 */
	public List<UserEntity> findAll();

	/**
	 * This method allows to save or update the specified user in the database.
	 *
	 * @param user		UserEntity is the user to save or update.
	 * @return			UserEntity the saved or updated user.
	 */
	@CacheEvict(value = "user", allEntries = true)
	@Override
	public UserEntity save(UserEntity user);
	
}
