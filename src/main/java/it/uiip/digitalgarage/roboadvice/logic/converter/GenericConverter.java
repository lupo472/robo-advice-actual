package it.uiip.digitalgarage.roboadvice.logic.converter;

public interface GenericConverter<E, DTO> {

	public E convertToEntity(DTO dto);
	
	public DTO convertToDTO(E entity);
	
}
