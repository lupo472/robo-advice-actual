package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface offers methods to retrieve data from the custom strategy table in the database.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Repository
@Transactional
public interface CustomStrategyRepository extends PagingAndSortingRepository<CustomStrategyEntity, Long> {

	static final String SET_STRATEGY_INACTIVE = "UPDATE CustomStrategyEntity ce SET ce.active = false WHERE ce.user = ?1 AND ce.active = true";

	/**
	 * This method allows to set inactive all the strategies of the selected user.
	 *
	 * @param user	UserEntity is the user for which you want to set the strategies inactive
	 */
	@Modifying
	@Query(SET_STRATEGY_INACTIVE)
	public void setStrategyInactive(UserEntity user);

	/**
	 * This method allows to retrieve all the strategies.
	 *
	 * @return	List of CustomStrategyEntities.
	 */
    public List<CustomStrategyEntity> findAll();

	/**
	 * This method allows to retrieve all the strategies for the selected user.
	 *
	 * @param user	UserEntity is the user for which you want to retrieve the strategies.
	 * @return		List of CustomStategyEntities.
	 */
	public List<CustomStrategyEntity> findByUser(UserEntity user);

	/**
	 * This method allows to retrieve the strategy for the selected user in a specific date.
	 *
	 * @param user	UserEntity is the user for which you want to retrieve the strategy.
	 * @param date	LocalDate is the date for which you want to retrieve the strategy.
	 * @return		List of CustomStrategyEntity that represents a single strategy.
	 */
    public List<CustomStrategyEntity> findByUserAndDate(UserEntity user, LocalDate date);

	/**
	 * This method allows to retrieve the active strategy for the selected user.
	 *
	 * @param user		UserEntity is the user for which you want to retrieve the strategy.
	 * @param active	Boolean that must be setted at true for the active strategy.
	 * @return			List of CustomStrategyEntity that represents the active strategy if active is true.
	 */
    public List<CustomStrategyEntity> findByUserAndActive(UserEntity user, boolean active);

	/**
	 * This method allows to retrieve the strategy for the selected user in a specific period between start and end.
	 *
	 * @param user		UserEntity is the user for which you want to retrieve the strategies.
	 * @param start		LocalDate is the starting date of the period for which you want to retrieve the strategies.
	 * @param end		LocalDate is the ending date of the period for which you want to retrieve the strategies.
	 * @return			List of CustomStrategyEntities.
	 */
	public List<CustomStrategyEntity> findByUserAndDateBetween(UserEntity user, LocalDate start, LocalDate end);
        
}
