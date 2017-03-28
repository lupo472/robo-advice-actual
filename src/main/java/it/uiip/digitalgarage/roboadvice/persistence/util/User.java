package it.uiip.digitalgarage.roboadvice.persistence.util;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import lombok.Data;

import java.util.List;

/**
 * This class represents an Object used in some operators to exchange informations about the user
 * with UserEntity user, List<PortfolioEntity> portfolio, List<CustomStrategyEntity> strategy
 * and CapitalEntity capital.
 *
 * @author Cristian Laurini
 */
public @Data class User {

	private UserEntity user;

	private List<PortfolioEntity> portfolio;

	private List<CustomStrategyEntity> strategy;

	private CapitalEntity capital;

}
