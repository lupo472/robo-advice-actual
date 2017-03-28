package it.uiip.digitalgarage.roboadvice.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Entity that represents the asset_class table on the database with Long id and String name.
 *
 * @author Cristian Laurini
 */
@Entity
@Table(name = "asset_class")
public @Data class AssetClassEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(name = "name", nullable = false, unique = true)
    private String name;

}
