package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class PortfolioOperator extends AbstractOperator {

    public PortfolioOperator(PortfolioRepository portfolioRepository){
        this.portfolioRep = portfolioRepository;
    }

    public PortfolioDTO getUserCurrentPortfolio(UserLoggedDTO dto) {
        LocalDate date = LocalDate.now();
        List<PortfolioEntity> entityList = this.portfolioRep.findByUserIdAndDate(dto.getId(), date);
        if(entityList.isEmpty()) {
        	return null;
        }
        PortfolioDTO response = this.portfolioWrap.wrapToDTO(entityList);
        return response;
    }

    public List<PortfolioDTO> getUserPortfolioPeriod(PortfolioRequestDTO dto){

        LocalDate initialDate = LocalDate.now();
        LocalDate finalDate = initialDate.minus(Period.ofDays(dto.getPeriod()));
        List<PortfolioEntity> entityList = this.portfolioRep.findByUserIdAndDateBetween(dto.getIdUser(), finalDate, initialDate);

        if(entityList.isEmpty()){
            return null;
        }

        List<PortfolioDTO> portfolioDTOList = new ArrayList<PortfolioDTO>();

        return null;
    }



}
