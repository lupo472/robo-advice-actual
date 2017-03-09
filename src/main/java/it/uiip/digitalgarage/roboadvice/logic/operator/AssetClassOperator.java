package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

@Service
public class AssetClassOperator extends AbstractOperator {
	
	public List<AssetClassDTO> getAssetClassSet() {
		List<AssetClassEntity> list = this.assetClassRep.findAllOrderById();
		return this.assetClassConv.convertToDTO(list);
	}

}
