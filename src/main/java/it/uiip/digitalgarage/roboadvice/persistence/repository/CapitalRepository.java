package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

@Repository
@Transactional
public interface CapitalRepository extends PagingAndSortingRepository<CapitalEntity, Long> {

	public List<CapitalEntity> findByUser(UserEntity user);
	
	public CapitalEntity findByUserAndDate(UserEntity user, LocalDate date);

	public List<CapitalEntity> findByUserAndDateBetween(UserEntity user, LocalDate finalDate, LocalDate initialDate);

}