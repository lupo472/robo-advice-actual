package it.uiip.digitalgarage.roboadvice.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "financial_data", indexes = {@Index(name = "IDX1", columnList = "id_asset, date")})
public @Data class FinancialDataEntity implements Comparable<FinancialDataEntity> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_asset", nullable = false)
    private AssetEntity asset;
	
	@Column(name = "value", nullable = false, precision = 14, scale = 4)
    private BigDecimal value;
	
	@Column(name = "date", nullable = false)
    private LocalDate date;

	@Override
	public int compareTo(FinancialDataEntity o) {
		return this.date.compareTo(o.getDate());
	}
}
