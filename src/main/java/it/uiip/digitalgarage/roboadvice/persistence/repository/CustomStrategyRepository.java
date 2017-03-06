package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CustomStrategyRepository extends PagingAndSortingRepository<CustomStrategyEntity, Long> {

    public List<CustomStrategyEntity> findAll();

    public List<CustomStrategyEntity> findByUserId(Long idUser);

    @Modifying
    @Query("UPDATE CustomStrategyEntity ce SET ce.active = false WHERE ce.user.id = ?1 AND ce.active = true")
    public void setStrategyInactive(Long userId);

    public List<CustomStrategyEntity> findByUserIdAndActive(Long idUser, boolean active);
    
}
