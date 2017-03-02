package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetDTO;

public class AssetOperator extends AbstractOperator {

	public AssetOperator(AssetRepository assetRep) {
		this.assetRep = assetRep;
	}
	
	public List<AssetDTO> getAssetSet() {
		List<AssetEntity> list = this.assetRep.findAll();
		return this.assetConv.convertToDTO(list);
	}
	
}
