package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

public class FinancialData {

    private Long id;
    private Long idAsset;
    private BigDecimal value;
    private String date;

    public FinancialData(Long idAsset, BigDecimal value, String date) {
        this.idAsset = idAsset;
        this.value = value;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAsset() {
        return idAsset;
    }

    public void setIdAsset(Long idAsset) {
        this.idAsset = idAsset;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
