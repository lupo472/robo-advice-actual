package it.uiip.digitalgarage.roboadvice.logic.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name="user")
public @Data class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private @Getter Long id;
	@NotNull
	private String email;
	@NotNull
	private String password;
	@Column
	private Timestamp timestamp;
		
}
