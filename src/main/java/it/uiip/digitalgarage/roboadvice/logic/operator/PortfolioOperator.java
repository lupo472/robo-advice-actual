package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.PortfolioRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

import java.time.LocalDate;
import java.util.List;

public class PortfolioOperator extends AbstractOperator {

    public PortfolioOperator(PortfolioRepository portfolioRepository){
        this.portfolioRep = portfolioRepository;
    }
    public PortfolioDTO getUserCurrentPortfolio(UserLoggedDTO dto) {
        LocalDate currentDate = LocalDate.now();
        List<PortfolioEntity> portfolioList = this.portfolioRep.findByUserIdAndDate(dto.getId(), currentDate);
        PortfolioDTO response = this.portfolioConv.convertToDTO(portfolioList);
        response.setIdUser(dto.getId());
        response.setDate(currentDate.toString());
        return response;
    }

}
