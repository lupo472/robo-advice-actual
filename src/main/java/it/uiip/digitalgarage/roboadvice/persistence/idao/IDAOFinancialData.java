package it.uiip.digitalgarage.roboadvice.persistence.idao;

import it.uiip.digitalgarage.roboadvice.logic.model.FinancialData;
import it.uiip.digitalgarage.roboadvice.persistence.model.DAOException;

public interface IDAOFinancialData {
	
	public void insertFinancialData(FinancialData financialData) throws DAOException;

}
