package it.uiip.digitalgarage.roboadvice.persistence.idao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.logic.model.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByEmail(String email);

}
