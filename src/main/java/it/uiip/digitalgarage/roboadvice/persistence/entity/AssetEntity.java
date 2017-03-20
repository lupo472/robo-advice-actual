package it.uiip.digitalgarage.roboadvice.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "asset")
public @Data class AssetEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_asset_class", nullable = false)
	private AssetClassEntity assetClass;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "data_source", nullable = false, unique = true)
	private String dataSource;
	
	@Column(name = "percentage", nullable = false, precision = 5, scale = 2)
	private BigDecimal percentage;
	
	@Column(name = "remarks_index", nullable = false)
	private int remarksIndex;
	
	@Column(name = "last_update")
	private LocalDate lastUpdate;
	
}
