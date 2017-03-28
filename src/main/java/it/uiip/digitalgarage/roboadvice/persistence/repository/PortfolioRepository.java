package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

import it.uiip.digitalgarage.roboadvice.persistence.util.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface offers methods to retrieve data from the portfolio table in the database.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Repository
@Transactional
public interface PortfolioRepository extends PagingAndSortingRepository<PortfolioEntity, Long> {
    
	static final String SUM_VALUES = "SELECT NEW it.uiip.digitalgarage.roboadvice.persistence.util.Value"
										+ "(p.date, SUM(p.value)) FROM PortfolioEntity p "
										+ "WHERE p.user = ?1 GROUP BY p.date";

	static final String SUM_VALUES_DATE = "SELECT NEW it.uiip.digitalgarage.roboadvice.persistence.util.Value"
											+ "(p.date, SUM(p.value)) FROM PortfolioEntity p "
											+ "WHERE p.user = ?1 AND p.date = ?2";

	static final String SUM_VALUES_ASSET_CLASS = "SELECT NEW it.uiip.digitalgarage.roboadvice.persistence.util.Value"
													+ "(p.date, SUM(p.value)) FROM PortfolioEntity p "
													+ "WHERE p.user = ?2 AND p.assetClass = ?1 "
													+ "GROUP BY p.date";

	static final String SUM_VALUES_ASSET_CLASS_DATE = "SELECT NEW it.uiip.digitalgarage.roboadvice.persistence.util.Value"
												+ "(p.date, SUM(p.value)) FROM PortfolioEntity p "
												+ "WHERE p.user = ?2 AND p.date = ?3 AND p.assetClass = ?1";

	/**
	 * This method allows to retrieve the sum of values for each set of portfolios of the specified user
	 * grouped by date.
	 *
	 * @param user	UserEntity is the user for which you get the values.
	 * @return		List of Value.
	 */
	@Query(value = SUM_VALUES)
	public List<Value> sumValues(UserEntity user);

	/**
	 * This method allows to retrieve the sum of values for each set of portfolios of the specified user
	 * in a specific date.
	 *
	 * @param user	UserEntity is the user for which you get the value.
	 * @param date	LocalDate is the date for which you get the value.
	 * @return		Value that has BigDecimal value and LocalDate date.
	 */
	@Query(value = SUM_VALUES_DATE)
	public Value sumValues(UserEntity user, LocalDate date);

	/**
	 * This method allows to retrieve the sum of values for each set of portfolios of the specified user
	 * and the specified asset class grouped by date.
	 *
	 * @param assetClass	AssetClassEntity is the assetClass for wich you get the values.
	 * @param user			UserEntity is the user for which you get the values.
	 * @return				List of Value.
	 */
	@Query(value = SUM_VALUES_ASSET_CLASS)
	public List<Value> sumValuesForAssetClass(AssetClassEntity assetClass, UserEntity user);

	/**
	 * This method allows to retrieve the sum of values for each set of portfolios of the specified user
	 * and the specified asset class in a specific date.
	 *
	 * @param assetClass	AssetClassEntity is the assetClass for wich you get the value.
	 * @param user			UserEntity is the user for which you get the value.
	 * @param date			LocalDate is the date for which you get the value.
	 * @return				Value that has BigDecimal value and LocalDate date.
	 */
	@Query(value = SUM_VALUES_ASSET_CLASS_DATE)
	public Value sumValuesForAssetClass(AssetClassEntity assetClass, UserEntity user, LocalDate date);

	/**
	 * This method allows to retrieve all the portfolios for a specific user.
	 *
	 * @param user	UserEntity is the user for which you want to retrieve the portfolios.
	 * @return		List of PortfolioEntities.
	 */
	public List<PortfolioEntity> findByUser(UserEntity user);

	/**
	 * This method allows to retrieve all the portfolios for a specific user in the selected date.
	 *
	 * @param user	UserEntity is the user for which you want to retrieve the portfolios.
	 * @param date	LocalDate is the date for which you want to retrieve the portfolios.
	 * @return		List of PortfolioEntities.
	 */
    public List<PortfolioEntity> findByUserAndDate(UserEntity user, LocalDate date);

	/**
	 * This method allows to retrieve all the portfolios for a specific user in selected period of time
	 * between initialDate and finalDate.
	 *
	 * @param user			UserEntity is the user for which you want to retrieve the portfolios.
	 * @param finalDate		LocalDate is the last date of the desired period.
	 * @param initialDate	LocalDate is the starting date of the desired period.
	 * @return				List of PortfolioEntities.
	 */
	public List<PortfolioEntity> findByUserAndDateBetween(UserEntity user, LocalDate finalDate, LocalDate initialDate);

	/**
	 * This method allows to retrieve the portfolio for a specific user and an asset in the selected date.
	 *
	 * @param user		UserEntity is the user for which you want to retrieve the portfolio.
	 * @param asset		AssetEntity is the asset for which you want to retrieve the portfolio.
	 * @param date		LocalDate is the date for which you want to retrieve the portfolio.
	 * @return			PortfolioEntity.
	 */
	public PortfolioEntity findByUserAndAssetAndDate(UserEntity user, AssetEntity asset, LocalDate date);
    
}
