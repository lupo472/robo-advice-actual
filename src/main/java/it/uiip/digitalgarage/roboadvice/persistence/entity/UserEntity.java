package it.uiip.digitalgarage.roboadvice.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Entity that represents the user table on the database with Long id, String email, String password,
 * LocalDate date and LocalDate lastUpdate.
 *
 * @author Cristian Laurini
 */
@Entity
@Table(name = "user")
public @Data class UserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@Column(name = "last_update")
	private LocalDate lastUpdate;
		
}
