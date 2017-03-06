package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.time.LocalDate;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;

@Repository
@Transactional
public interface CapitalRepository extends PagingAndSortingRepository<CapitalEntity, Long> {
	
	public CapitalEntity findByUserIdAndDate(Long userId, LocalDate date);
	
	//TODO
	public void updateCapital(Long idUser, LocalDate date);
	
}
