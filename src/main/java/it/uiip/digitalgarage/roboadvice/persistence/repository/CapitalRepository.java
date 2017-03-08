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
	
	static final String UPDATE_CAPITAL = "UPDATE capital c SET c.amount = ?3 WHERE c.id_user = ?1 AND c.date = ?2";
	
	static final String FIND_LAST = "SELECT * FROM capital WHERE id_user = ?1 AND date = "
								  + "(SELECT max(date) FROM capital WHERE id_user = ?1)";

	public CapitalEntity findByUserIdAndDate(Long userId, LocalDate date);
	
	@Query(value = FIND_LAST, nativeQuery = true)
	public CapitalEntity findLast(Long idUser);
	
	@Modifying
	@Query(value = UPDATE_CAPITAL, nativeQuery =  true)
	public void updateCapital(Long userId, String date, BigDecimal amount);

}
