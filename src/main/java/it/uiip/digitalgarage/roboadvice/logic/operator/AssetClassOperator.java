package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.AssetClassRepository;

public class AssetClassOperator extends GenericOperator {
	
	public AssetClassOperator(AssetClassRepository assetClassRep) {
		this.assetClassRep = assetClassRep;
	}
	
	public List<AssetClassEntity> getAssetClassSet() {
		return this.assetClassRep.findAll();
	}

}
