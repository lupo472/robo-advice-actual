package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

/**
 * This class offers methods to convert AssetClassEntities in AssetClassDTOs
 *
 * @author Cristian Laurini
 */
@Component
public class AssetClassConverter {

	/**
	 * This method converts a single AssetClassEntity in an AssetClassDTO.
	 *
	 * @param entity	AssetClassEntity is the entity to convert.
	 * @return			AssetClassDTO is the result of the conversion.
	 */
	public AssetClassDTO convertToDTO(AssetClassEntity entity) {
		AssetClassDTO dto = new AssetClassDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	/**
	 * This method converts a List of AssetClassEntities in a List of AssetClassDTOs.
	 *
	 * @param entity	List of AssetClassEntities is the list of entities to convert.
	 * @return			List of AssetClassDTOs is the result of the conversion.
	 */
	public List<AssetClassDTO> convertToDTO(List<AssetClassEntity> entity) {
		List<AssetClassDTO> dto = new ArrayList<>();
		for (AssetClassEntity assetClassEntity : entity) {
			dto.add(this.convertToDTO(assetClassEntity));
		}
		Collections.sort(dto);
		return dto;
	}

}
