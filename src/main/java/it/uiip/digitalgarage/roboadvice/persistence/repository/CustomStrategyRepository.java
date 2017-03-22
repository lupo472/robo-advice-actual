package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface CustomStrategyRepository extends PagingAndSortingRepository<CustomStrategyEntity, Long> {

    public List<CustomStrategyEntity> findAll();

    public List<CustomStrategyEntity> findByUser(UserEntity user);

    @Modifying
    @Query("UPDATE CustomStrategyEntity ce SET ce.active = false WHERE ce.user = ?1 AND ce.active = true")
    public void setStrategyInactive(UserEntity user);
    
    public List<CustomStrategyEntity> findByUserAndDate(UserEntity user, LocalDate date);

    public List<CustomStrategyEntity> findByUserAndActive(UserEntity user, boolean active);
    
    public List<CustomStrategyEntity> findByUserAndDateBetween(UserEntity user, LocalDate start, LocalDate end);
        
}
