package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

public class AssetClassOperator extends AbstractOperator {
	
	public AssetClassOperator(AssetClassRepository assetClassRep) {
		this.assetClassRep = assetClassRep;
	}
	
	public List<AssetClassDTO> getAssetClassSet() {
		List<AssetClassEntity> list = this.assetClassRep.findAll();
		return this.assetClassConv.convertToDTO(list);
	}

}
