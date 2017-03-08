package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;

@Service
public class AssetOperator extends AbstractOperator {

	public List<AssetDTO> getAssetSet() {
		List<AssetEntity> list = this.assetRep.findAll();
		return this.assetConv.convertToDTO(list);
	}
	
}
