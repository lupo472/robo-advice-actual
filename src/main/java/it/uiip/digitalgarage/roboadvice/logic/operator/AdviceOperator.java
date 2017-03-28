package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.User;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class manages the computation for giving an Advice.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Service
public class AdviceOperator extends AbstractOperator {

    @Autowired
    private ForecastingOperator forecastingOp;

    @Autowired
    private DefaultStrategyOperator defaultStrategyOp;

    /*
     * Performances for this method are not good
     * TODO: improve if necessary
     */

	/**
	 * This method returns an adviced strategy for the user.
	 *
	 * @param period	PeriodDTO is the number of days to retrieve.
	 * @param auth		Authentication is used to retrieve the logged user.
	 * @return			DefaultStrategyDTO if some defaultStrategy is better than the current strategy
	 * 					of the logged user or null instead.
	 */
    @Cacheable("advice")
    public DefaultStrategyDTO getAdvice(PeriodDTO period, Authentication auth) {
        UserEntity userEntity = this.userRep.findByEmail(auth.getName());
        List<PortfolioDTO> currentStrategyDemo = this.forecastingOp.getDemo(period, auth);
        BigDecimal finalCapital = new BigDecimal(0);
        if(currentStrategyDemo != null) {
            int lastElementIndex = currentStrategyDemo.size() - 1;
            finalCapital = this.portfolioSum(currentStrategyDemo.get(lastElementIndex));
        }
        List<DefaultStrategyDTO> defaultStrategyList = this.defaultStrategyOp.getDefaultStrategySet();
        DefaultStrategyDTO result = null;
		User user = new User();
		user.setUser(userEntity);
        for(DefaultStrategyDTO defaultStrategy : defaultStrategyList) {
            CustomStrategyDTO customStrategy = new CustomStrategyDTO();
            customStrategy.setList(defaultStrategy.getList());
            List<CustomStrategyEntity> strategyList = this.customStrategyWrap.unwrapToEntity(customStrategy);
			user.setStrategy(strategyList);
            List<PortfolioDTO> defaultStrategyDemo = this.forecastingOp.getDemo(user, period);
            int lastElementIndex = defaultStrategyDemo.size() - 1;
            BigDecimal defaultStrategyCapital = this.portfolioSum(defaultStrategyDemo.get(lastElementIndex));
            if(defaultStrategyCapital.doubleValue() > finalCapital.doubleValue()) {
                finalCapital = defaultStrategyCapital;
            	result = defaultStrategy;
            }
        }
        return result;
    }

    private BigDecimal portfolioSum(PortfolioDTO portfolioDTO) {
        BigDecimal sum = new BigDecimal(0);
        for(PortfolioElementDTO element : portfolioDTO.getList()) {
            sum = sum.add(element.getValue());
        }
        return sum;
    }

}
