package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetRepository;

public class AssetOperator extends GenericOperator {

	public AssetOperator(AssetRepository assetRep) {
		this.assetRep = assetRep;
	}
	
	public List<AssetEntity> getAssetSet() {
		return this.assetRep.findAll();
	}
	
}
