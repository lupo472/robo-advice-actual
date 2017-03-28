package it.uiip.digitalgarage.roboadvice.service.util;

/**
 * This class represents the response of the APIs with an int response and a field called data that is a generic type.
 *
 * @author Cristian Laurini
 */
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