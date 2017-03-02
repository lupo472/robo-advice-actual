package it.uiip.digitalgarage.roboadvice.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "custom_strategy")
public @Data class CustomStrategyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_asset_class",nullable = false)
    private AssetClassEntity assetClass;

    @Column(name = "percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "date", nullable = false)
    private LocalDate date;

}
