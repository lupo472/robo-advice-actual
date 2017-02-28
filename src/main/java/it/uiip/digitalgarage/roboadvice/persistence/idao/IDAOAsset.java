package it.uiip.digitalgarage.roboadvice.persistence.idao;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.logic.model.Asset;
import it.uiip.digitalgarage.roboadvice.persistence.model.DAOException;

public interface IDAOAsset {
	
	public List<Asset> getAssets() throws DAOException;

}
