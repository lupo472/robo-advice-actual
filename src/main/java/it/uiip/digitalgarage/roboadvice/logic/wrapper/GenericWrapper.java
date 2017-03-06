package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import java.util.List;

public interface GenericWrapper<E, DTO> {
	
	public List<E> unwrapToEntity(DTO dto);
	
	public DTO wrapToDTO(List<E> entityList);

}
