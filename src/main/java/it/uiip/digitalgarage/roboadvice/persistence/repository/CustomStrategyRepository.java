package it.uiip.digitalgarage.roboadvice.persistence.repository;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

/*
 * Created by Luca on 02/03/2017.
 */


@Repository
@Transactional
public interface CustomStrategyRepository extends PagingAndSortingRepository<CustomStrategyEntity, Long> {

    public List<CustomStrategyEntity> findAll();

    public List<CustomStrategyEntity> findByUser(UserEntity user);

    public CustomStrategyEntity save(CustomStrategyEntity customStrategyEntity);

}
