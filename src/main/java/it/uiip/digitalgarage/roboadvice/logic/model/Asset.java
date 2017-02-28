package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

public class Asset {
	
	private Long id;
	private Long idAssetClass;
	private String dataSource;
	private BigDecimal percentage;
	private int remarksIndex;
	
	public Asset(Long id, String dataSource, int remarksIndex) {
		this.id = id;
		this.dataSource = dataSource;
		this.remarksIndex = remarksIndex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public int getRemarksIndex() {
		return remarksIndex;
	}

	public void setRemarksIndex(int remarksIndex) {
		this.remarksIndex = remarksIndex;
	}

	public Long getIdAssetClass() {
		return idAssetClass;
	}

	public void setIdAssetClass(Long idAssetClass) {
		this.idAssetClass = idAssetClass;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

}
