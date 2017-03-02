package it.uiip.digitalgarage.roboadvice.logic.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="asset")
public @Data class AssetEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "id_asset_class", nullable = false)
	private AssetClassEntity assetClass;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "data_source", nullable = false, unique = true)
	private String dataSource;
	
	@Column(name = "percentage", nullable = false)
	private BigDecimal percentage;
	
	@Column(name = "remarks_index", nullable = false)
	private int remarksIndex;
	
}
