package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.persistence.util.User;
import it.uiip.digitalgarage.roboadvice.service.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * This class manages all the computations related to the Portfolio.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Service
public class PortfolioOperator extends AbstractOperator {

	/**
	 * This method allows to retrieve the current portfolio for the logged user.
	 *
	 * @param auth	Authentication is used to retrieve the logged user.
	 * @return		PortfolioDTO that represents the current portfolio or null if the users
	 * 				doesn't have a portfolio.
	 */
	@Cacheable("portfolio")
    public PortfolioDTO getCurrentPortfolio(Authentication auth) {
		UserEntity user = this.userRep.findByEmail(auth.getName());
		return this.getCurrentPortfolio(user);
	}

	/**
	 * This method allows to retrieve the current portfolio for the selected user.
	 *
	 * @param user	UserEntity is the user for which retrieve the current portfolio.
	 * @return		PortfolioDTO that represents the current portfolio or null if the users
	 * 				doesn't have a portfolio.
	 */
	@Cacheable("portfolio")
	public PortfolioDTO getCurrentPortfolio(UserEntity user) {
		List<PortfolioEntity> portfolioEntityList = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
		if(portfolioEntityList.isEmpty()) {
			return null;
		}
		Map<Long, BigDecimal> assetClassMap = new HashMap<>();
		for(PortfolioEntity portfolio : portfolioEntityList) {
			if(assetClassMap.get(portfolio.getAssetClass().getId()) == null) {
				assetClassMap.put(portfolio.getAssetClass().getId(), this.portfolioRep.sumValuesForAssetClass(portfolio.getAssetClass(), user, user.getLastUpdate()).getValue());
			}
		}
		BigDecimal totalValue = this.portfolioRep.sumValues(user, user.getLastUpdate()).getValue();
		return this.portfolioWrap.wrapToDTO(portfolioEntityList, totalValue, assetClassMap);
	}

	/**
	 * This method allows to retrieve the history of the portfolio for the logged user in the selected period.
	 *
	 * @param period	PeriodDTO is the number of days to retrieve.
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @return			List of PortfolioDTOs or null if the user doesn't have any portfolio
	 * 					in the selected period.
	 */
	@Cacheable("portfolioHistory")
    public List<PortfolioDTO> getPortfolioForPeriod(PeriodDTO period, Authentication auth){
		UserEntity userEntity = this.userRep.findByEmail(auth.getName());
		List<PortfolioEntity> portfolioEntityList = null;
		if(period.getPeriod() == 0){
			portfolioEntityList = this.portfolioRep.findByUser(userEntity);
		} else {
			LocalDate initialDate = LocalDate.now();
			LocalDate finalDate = initialDate.minus(Period.ofDays(period.getPeriod() - 1));
			portfolioEntityList = this.portfolioRep.findByUserAndDateBetween(userEntity, finalDate, initialDate);
		}
		if (portfolioEntityList.isEmpty()) {
			return null;
		}
		Map<LocalDate, BigDecimal> totalPerDateMap = Mapper.getMapValues(this.portfolioRep.sumValues(userEntity));
		Map<Long, Map<LocalDate, BigDecimal>> valuePerDatePerAssetClassMap = new HashMap<>();
		User user = new User();
		user.setUser(userEntity);
		user.setPortfolio(portfolioEntityList);
		Map<String, Set<PortfolioEntity>> portfolioSetPerDateMap = this.createMap(user, valuePerDatePerAssetClassMap);
		List<PortfolioDTO> result = this.portfolioWrap.wrapToDTOList(valuePerDatePerAssetClassMap, portfolioSetPerDateMap, totalPerDateMap);
		Collections.sort(result);
        return result;
    }

	private Map<String, Set<PortfolioEntity>> createMap(User user, Map<Long, Map<LocalDate, BigDecimal>> assetClassMap) {
		Map<String, Set<PortfolioEntity>> portfolioSetPerDateMap = new HashMap<>();
		for (PortfolioEntity portfolio : user.getPortfolio()) {
			if(assetClassMap.get(portfolio.getAssetClass().getId()) == null) {
				assetClassMap.put(portfolio.getAssetClass().getId(), Mapper.getMapValues(this.portfolioRep.sumValuesForAssetClass(portfolio.getAssetClass(), user.getUser())));
			}
			if(portfolioSetPerDateMap.get(portfolio.getDate().toString()) == null) {
				portfolioSetPerDateMap.put(portfolio.getDate().toString(), new HashSet<>());
			}
			portfolioSetPerDateMap.get(portfolio.getDate().toString()).add(portfolio);
		}
		return portfolioSetPerDateMap;
	}

	/**
	 * This method creates and save on the database a portfolio for a new user.
	 *
	 * @param user						User is the user for which you want to create the portfolio, and contains the capital
	 *                      			of the user and his strategy.
	 * @param assetsPerClassMap					Map with asset class id as key and a List of AssentEntities as value.
	 * @param financialDataPerAssetMap	Map with asset id as key and FinancialDataEntity as value.
	 * @return							Boolean is true if everything is ok, is false instead if the user doesn't have
	 * 									a capital or an active strategy.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
    public boolean createUserPortfolio(User user, Map<Long, List<AssetEntity>> assetsPerClassMap, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
		if(user.getStrategy().isEmpty()) {
			return false;
		}
    	if(user.getCapital() == null) {
    		return false;
    	}
    	BigDecimal amount = user.getCapital().getAmount();
    	for (CustomStrategyEntity strategy : user.getStrategy()) {
			BigDecimal amountPerClass = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(strategy.getPercentage());
			AssetClassEntity assetClass = strategy.getAssetClass();
			this.savePortfolioForAssetClass(user.getUser(), assetClass, amountPerClass, assetsPerClassMap, financialDataPerAssetMap);
		}
    	return true;
    }

    private void savePortfolioForAssetClass(UserEntity user, AssetClassEntity assetClass, BigDecimal amount,
											Map<Long, List<AssetEntity>> assetsPerClassMap,
											Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
    	List<AssetEntity> assets = assetsPerClassMap.get(assetClass.getId());
    	List<PortfolioEntity> portfolioList = this.portfolioRep.findByUserAndDate(user, LocalDate.now());
		Map<Long, PortfolioEntity> portfolioPerAssetMap = Mapper.getMapPortfolio(portfolioList);
    	for (AssetEntity asset : assets) {
			PortfolioEntity savedPortfolio = portfolioPerAssetMap.get(asset.getId());
			PortfolioEntity newPortfolio = new PortfolioEntity();
			if(savedPortfolio != null) {
				newPortfolio = savedPortfolio;
			}
    		BigDecimal amountPerAsset = amount.divide(new BigDecimal(100.00), 8, RoundingMode.HALF_UP).multiply(asset.getPercentage());
    		newPortfolio.setAsset(asset);
    		newPortfolio.setAssetClass(assetClass);
    		newPortfolio.setUser(user);
    		newPortfolio.setValue(amountPerAsset);
    		newPortfolio.setUnits(this.getUnitsForAsset(asset, amountPerAsset, financialDataPerAssetMap));
    		newPortfolio.setDate(LocalDate.now());
			this.portfolioRep.save(newPortfolio);
    	}
    }

    private BigDecimal getUnitsForAsset(AssetEntity asset, BigDecimal amount, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
    	FinancialDataEntity financialData = financialDataPerAssetMap.get(asset.getId());
		BigDecimal units = amount.divide(financialData.getValue(), 8, RoundingMode.HALF_UP);
		return units;
    }

	/**
	 * This method evaluates a portfolio.
	 *
	 * @param financialDataPerAssetMap	Map with asset id as key and a FinancialDataEntity as value.
	 * @param portfolio					List of PortfolioEntities.
	 * @return							BigDecimal that is the sum of values of each PortfolioEntity or null
	 * 									if portfolio is empty.
	 */
    public BigDecimal evaluatePortfolio(Map<Long, FinancialDataEntity> financialDataPerAssetMap, List<PortfolioEntity> portfolio) {
		if(portfolio.isEmpty()) {
    		return null;
    	}
    	BigDecimal result = new BigDecimal(0);
    	for (PortfolioEntity element : portfolio) {
			FinancialDataEntity financialData = financialDataPerAssetMap.get(element.getAsset().getId());
			BigDecimal amountPerAsset = element.getUnits().multiply(financialData.getValue());
			result = result.add(amountPerAsset);
		}
    	return result;
    }

	/**
	 * This method computes a portfolio with the new financial data.
	 *
	 * @param user						User is the user for which compute the portfolio containing the current portfolio.
	 * @param financialDataPerAssetMap	Map with asset id as key and a FinancialDataEntity as value.
	 * @return							List of PortfolioEntities or null if some problem occurs.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
	public List<PortfolioEntity> computeUserPortfolio(User user, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
    	List<PortfolioEntity> newPortfolioList = new ArrayList<>();
    	for (PortfolioEntity element : user.getPortfolio()) {
    		BigDecimal units = element.getUnits();
    		BigDecimal newValue = this.getValueForAsset(units, element.getAsset(), financialDataPerAssetMap);
    		if(newValue == null) {
    			return null;
    		}
    		PortfolioEntity newElement = new PortfolioEntity();
    		newElement.setAsset(element.getAsset());
    		newElement.setAssetClass(element.getAssetClass());
    		newElement.setUser(user.getUser());
    		newElement.setUnits(units);
    		newElement.setValue(newValue);
    		newElement.setDate(LocalDate.now());
    		newPortfolioList.add(newElement);
    	}
    	this.savePortfolio(newPortfolioList);
    	return newPortfolioList;
    }

     private BigDecimal getValueForAsset(BigDecimal units, AssetEntity asset, Map<Long, FinancialDataEntity> financialDataPerAssetMap) {
    	FinancialDataEntity financialData = financialDataPerAssetMap.get(asset.getId());
    	if(financialData == null) {
    		return null;
    	}
    	BigDecimal result = units.multiply(financialData.getValue());
    	return result;
    }

	/**
	 * This method saves a List of PortfolioEntities on the database.
	 *
	 * @param portfolioList	List of PortfolioEntities to save.
	 */
	@CacheEvict(value = {"activeStrategy", "strategies", "portfolio", "portfolioHistory", "currentCapital", "capitalHistory", "backtesting", "forecast", "demo", "advice"}, allEntries = true)
    public void savePortfolio(List<PortfolioEntity> portfolioList) {
    	for (PortfolioEntity portfolio : portfolioList) {
    		PortfolioEntity savedPortfolio = this.portfolioRep.findByUserAndAssetAndDate(portfolio.getUser(), portfolio.getAsset(), LocalDate.now());
    		if(savedPortfolio != null) {
	    		this.portfolioRep.delete(savedPortfolio);
	    	}
    		this.portfolioRep.save(portfolio);
		}
    }
    
}
