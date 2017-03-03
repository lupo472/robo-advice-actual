package it.uiip.digitalgarage.roboadvice.persistence.entity;

import java.math.BigDecimal;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "portfolio")
public @Data class PortfolioEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity idUser;

    @ManyToOne
    @JoinColumn(name = "id_asset", nullable = false)
    private AssetEntity idAsset;

    @ManyToOne
    @JoinColumn(name = "id_asset_class", nullable = false)
    private AssetClassEntity idAssetClass;

    @Column(name = "units", nullable = false, precision = 14, scale = 4)
    private BigDecimal units;

    @Column(name = "percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal value;

    @Column(name = "date", nullable = false)
    private String date;

}
