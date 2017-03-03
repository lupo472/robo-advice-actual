package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
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

    public List<CustomStrategyEntity> findByUserId(Long idUser);

    @Modifying
//    @Query(value="UPDATE custom_strategy c SET c.active = false WHERE c.id_user = ?1 AND c.active = true", nativeQuery = true)
    @Query("UPDATE CustomStrategyEntity ce SET ce.active = false WHERE ce.user.id = ?1 AND ce.active = true")
    public void setNewActiveForCustomStrategy(Long userId);

}
