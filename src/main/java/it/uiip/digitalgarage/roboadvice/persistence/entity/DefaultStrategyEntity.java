package it.uiip.digitalgarage.roboadvice.persistence.entity;

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
@Table(name = "default_strategy")
public @Data class DefaultStrategyEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(name = "name", nullable = false, unique = true)
    private String name;
	
	@ManyToOne()
	@JoinColumn(name = "id_asset_class", nullable = false)
	private AssetClassEntity assetClass;
	
	@Column(name = "percentage", nullable = false)
    private BigDecimal percentage;

}
