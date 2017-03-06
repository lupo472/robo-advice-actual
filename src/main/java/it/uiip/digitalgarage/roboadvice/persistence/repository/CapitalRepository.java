package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;

@Repository
@Transactional
public interface CapitalRepository extends PagingAndSortingRepository<CapitalEntity, Long> {
	
	public CapitalEntity findByUserIdAndDate(Long userId, LocalDate date);
	
	@Modifying
	@Query(value = "UPDATE capital c SET c.amount = ?3 WHERE c.id_user = ?1 AND c.date = ?2", nativeQuery =  true)
	public void updateCapital(Long userId, String date, BigDecimal amount);
	
}
