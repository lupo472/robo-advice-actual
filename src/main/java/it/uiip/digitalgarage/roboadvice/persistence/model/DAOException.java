package it.uiip.digitalgarage.roboadvice.persistence.model;

/**
 * 
 * Exception thrown when connection with database is not available.
 *
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = -2015309776596439265L;

	public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String s) {
        super(s);
    }

    public DAOException() {
        super();
    }
    
}
