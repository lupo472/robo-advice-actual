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
@Table(name = "capital", indexes = {@Index(name = "IDX1", columnList = "id_user, date")})
public @Data class CapitalEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;
	
	@Column(name = "amount", nullable = false, precision = 14, scale = 4)
	private BigDecimal amount;
	
	@Column(name = "date")
    private LocalDate date;

}
