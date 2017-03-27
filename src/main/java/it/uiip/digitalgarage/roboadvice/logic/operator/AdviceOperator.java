package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Luca on 27/03/2017.
 */

@Service
public class AdviceOperator extends AbstractOperator {

    @Autowired
    private ForecastingOperator forecastingOp;

    @Autowired
    private PortfolioOperator portfolioOp;

    @Autowired
    private DefaultStrategyOperator defaultStrategyOp;

//    public Map<DefaultStrategyEntity, CapitalDTO> computeDefaultStrategyForecastCapital(List<FinancialDataDTO> forecastFinancialData) {
//        PeriodDTO periodRequest = new PeriodDTO();
//        periodRequest.setPeriod(14);
//        return null;
//    }

    public DefaultStrategyDTO getAdvice(PeriodDTO period, Authentication auth /*UserEntity user, Map<DefaultStrategyEntity, CapitalDTO> defaultStrategiesForecastedCapitals, List<FinancialDataDTO> forecastFinancialData*/) {
        UserEntity user = this.userRep.findByEmail(auth.getName());
        List<PortfolioDTO> currentStrategyPortfolio = this.forecastingOp.getDemo(period, auth);
        BigDecimal finalCapital = new BigDecimal(0);
        if(currentStrategyPortfolio != null) {
            int lastElementIndex = currentStrategyPortfolio.size() - 1;
            finalCapital = this.portfolioSum(currentStrategyPortfolio.get(lastElementIndex));
        }
        List<DefaultStrategyDTO> getDefaultStrategySet = this.defaultStrategyOp.getDefaultStrategySet();
        DefaultStrategyDTO result = null;
        for(DefaultStrategyDTO defaultStrategy : getDefaultStrategySet) {
            CustomStrategyDTO customStrategyDTO = new CustomStrategyDTO();
            customStrategyDTO.setList(defaultStrategy.getList());
            List<CustomStrategyEntity> strategyList = this.customStrategyWrap.unwrapToEntity(customStrategyDTO);
            List<PortfolioDTO> defaultStrategyPortfolio = this.forecastingOp.getDemo(strategyList, period, user);
            if(defaultStrategyPortfolio != null) {
                int lastElementIndex = defaultStrategyPortfolio.size() - 1;
                BigDecimal defaultStrategyCapital = this.portfolioSum(defaultStrategyPortfolio.get(lastElementIndex));
                if(defaultStrategyCapital.doubleValue() > finalCapital.doubleValue()) {
                    result = defaultStrategy;
                }
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
