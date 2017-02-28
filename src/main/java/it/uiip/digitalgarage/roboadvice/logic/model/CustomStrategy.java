package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;

public class CustomStrategy {

    private Long id;
    private Long idUser;
    private BigDecimal bonds;
    private BigDecimal stocks;
    private BigDecimal forex;
    private BigDecimal commodities;
    private boolean active;
    private String timestamp;

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

    public BigDecimal getBonds() {
        return bonds;
    }

    public void setBonds(BigDecimal bonds) {
        this.bonds = bonds;
    }

    public BigDecimal getStocks() {
        return stocks;
    }

    public void setStocks(BigDecimal stocks) {
        this.stocks = stocks;
    }

    public BigDecimal getForex() {
        return forex;
    }

    public void setForex(BigDecimal forex) {
        this.forex = forex;
    }

    public BigDecimal getCommodities() {
        return commodities;
    }

    public void setCommodities(BigDecimal commodities) {
        this.commodities = commodities;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
