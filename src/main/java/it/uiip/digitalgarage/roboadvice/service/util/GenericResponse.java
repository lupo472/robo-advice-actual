package it.uiip.digitalgarage.roboadvice.service.util;

public class GenericResponse<T> {

	private int response;
	private T data;

	public GenericResponse(int response, T data) {
		this.response = response;
		this.data = data;
	}

	public int getResponse() {
		return this.response;
	}

	public T getData() {
		return this.data;
	}

}