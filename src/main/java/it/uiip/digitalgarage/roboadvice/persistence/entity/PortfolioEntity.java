package it.uiip.digitalgarage.roboadvice.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "portfolio", indexes = {@Index(name = "IDX1", columnList = "id_user, date"),
									  @Index(name = "IDX2", columnList = "id_user, id_asset, date")})
public @Data class PortfolioEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_asset", nullable = false)
    private AssetEntity asset;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_asset_class", nullable = false)
    private AssetClassEntity assetClass;

    @Column(name = "units", nullable = false, precision = 14, scale = 4)
    private BigDecimal units;

    @Column(name = "value", nullable = false, precision = 14, scale = 4)
    private BigDecimal value;

    @Column(name = "date", nullable = false)
    private LocalDate date;

}
