package it.uiip.digitalgarage.roboadvice.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uiip.digitalgarage.roboadvice.logic.model.FinancialData;
import it.uiip.digitalgarage.roboadvice.persistence.idao.IDAOFinancialData;
import it.uiip.digitalgarage.roboadvice.persistence.model.DAOException;
import it.uiip.digitalgarage.roboadvice.persistence.util.DataSource;

public class DAOFinancialData implements IDAOFinancialData {
	
	private static final String INSERT_FINANCIAL_DATA = "INSERT INTO financial_data(id_asset, value, date) VALUES(?, ?, ?);";

	@Override
	public void insertFinancialData(FinancialData financialData) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DataSource.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_FINANCIAL_DATA);
			preparedStatement.setLong(1, financialData.getIdAsset());
			preparedStatement.setBigDecimal(2, financialData.getValue());
			preparedStatement.setString(3, financialData.getDate());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			DataSource.close(connection);
			DataSource.close(preparedStatement);
		}
	}

}
