package it.uiip.digitalgarage.roboadvice.persistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.uiip.digitalgarage.roboadvice.persistence.model.DAOConstant;
import it.uiip.digitalgarage.roboadvice.persistence.model.DAOException;

/**
*
* @author cristianlaurini
*/

public class DataSource {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        	
        }
    }
    
    private static final String URI = "jdbc:mysql://localhost:3306/roboadvice?useSSL=false";
    private static final String USERNAME = "myuser";
    private static final String PASSWORD = "myuser";

    public static Connection getConnection() throws DAOException {
        try {
            return DriverManager.getConnection(URI, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            throw new DAOException(DAOConstant.CONNECTION_ERROR.getError());
        }
    }

    public static void close(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
        }
    }

    public static void close(Statement s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (SQLException e) {
        }
    }
    
    public static void close(PreparedStatement p) {
        try {
            if (p != null) {
                p.close();
            }
        } catch (SQLException e) {
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
        }
    }

}
