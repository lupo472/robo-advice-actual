package it.uiip.digitalgarage.roboadvice.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auth")
public class AuthEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_user", nullable = false, unique = true)
    private UserEntity user;
	
	@Column(name = "token", nullable = false, unique = true)
    private String percentage;
	
	@Column(name = "date", nullable = false)
    private LocalDate date;
	
}
