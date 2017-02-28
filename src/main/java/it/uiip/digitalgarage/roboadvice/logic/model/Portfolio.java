package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

public class Portfolio {
    private Long id;
    private Long idUser;
    private Long id_strategy;
    private Long idAsset;
    private BigDecimal units;
    private BigDecimal value;
    private String date;

    public Portfolio(Long id, Long idUser, Long id_strategy, Long idAsset, BigDecimal units, BigDecimal value, String date) {
        this.id = id;
        this.idUser = idUser;
        this.id_strategy = id_strategy;
        this.idAsset = idAsset;
        this.units = units;
        this.value = value;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getId_strategy() {
        return id_strategy;
    }

    public void setId_strategy(Long id_strategy) {
        this.id_strategy = id_strategy;
    }

    public Long getIdAsset() {
        return idAsset;
    }

    public void setIdAsset(Long idAsset) {
        this.idAsset = idAsset;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
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
