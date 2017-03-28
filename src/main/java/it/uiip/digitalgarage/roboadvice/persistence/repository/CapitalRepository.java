package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

/**
 * This interface offers methods to retrieve data from the capital table in the database.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Repository
@Transactional
public interface CapitalRepository extends PagingAndSortingRepository<CapitalEntity, Long> {

	/**
	 * This method allows to retrieve all the capitals for the selected user.
	 *
	 * @param user	UserEntity is the user for which you want to retrieve the capitals.
	 * @return		List of CapitalEntities.
	 */
	public List<CapitalEntity> findByUser(UserEntity user);

	/**
	 * This method allows to retrieve the capital for the selected user in a specific date.
	 *
	 * @param user		UserEntity is the user for which you want to retrieve the capital.
	 * @param date		LocalDate is the date for which you want to retrieve the capital.
	 * @return			CapitalEntity.
	 */
	public CapitalEntity findByUserAndDate(UserEntity user, LocalDate date);

	/**
	 * This method allows to retrieve all the capitals for the selected user in a specific period of time between
	 * initialDate and finalDate.
	 *
	 * @param user			UserEntity is the user for which you want to retrieve the capitals.
	 * @param finalDate		LocalDate is the last date of the period for which you want to retrieve the capitals.
	 * @param initialDate	LocalDate is the starting date of the period for which you want to retrieve the capitals.
	 * @return				List of CapitalEntities.
	 */
	public List<CapitalEntity> findByUserAndDateBetween(UserEntity user, LocalDate finalDate, LocalDate initialDate);

}