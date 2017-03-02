package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.List;

public interface GenericConverter<E, DTO> {

	public E convertToEntity(DTO dto);
	
	public DTO convertToDTO(E entity);
	
	public List<E> convertToEntity(List<DTO> dto);
	
	public List<DTO> convertToDTO(List<E> entity);
	
}
