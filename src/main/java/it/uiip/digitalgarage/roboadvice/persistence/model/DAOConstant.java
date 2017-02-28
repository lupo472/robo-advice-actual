package it.uiip.digitalgarage.roboadvice.persistence.model;

public enum DAOConstant {

	CONNECTION_ERROR("connection error", -1);	
	
	private final String constantName;
	private final int errorCode;
	
	private DAOConstant(String constantName, int daoError){
		this.constantName = constantName;
		this.errorCode = daoError;
	}
	
	public String getError(){
		return this.constantName;
	}
	
	public int getErrorCode(){
		return this.errorCode;
	}
	
}
