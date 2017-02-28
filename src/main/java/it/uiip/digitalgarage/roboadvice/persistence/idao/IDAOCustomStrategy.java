package it.uiip.digitalgarage.roboadvice.persistence.idao;

import it.uiip.digitalgarage.roboadvice.logic.model.CustomStrategy;
import it.uiip.digitalgarage.roboadvice.persistence.model.DAOException;

import java.util.List;

/**
 * Created by Luca on 28/02/2017.
 */
public interface IDAOCustomStrategy {

    public List<CustomStrategy> getCustomStrategies() throws DAOException;
}
