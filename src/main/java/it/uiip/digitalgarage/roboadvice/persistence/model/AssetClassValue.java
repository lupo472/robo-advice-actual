package it.uiip.digitalgarage.roboadvice.persistence.model;

import java.math.BigDecimal;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

public @Data @AllArgsConstructor class AssetClassValue {
	
	private AssetClassEntity assetClass;	
	
	private BigDecimal value;
	
}
