package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.LoginDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserDTO;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;

/**
 * This class contains methods to allow registration and login of an user or to get the list of users.
 *
 * @author Cristian Laurini
 * @author Luca Antilici
 */
@Service
public class UserOperator extends AbstractOperator {

	/**
	 * This method allows the registration of a new user.
	 *
	 * @param user	UserDTO is the user to register.
	 * @return		Boolean that is true if the registration is completed without problems, or false if
	 * 				the specified email is already registered.
	 */
	public boolean registerUser(UserDTO user) {
		if(this.isRegistered(user.getEmail())) {
			return false;
		}
		UserEntity userEntity = this.userConv.convertToEntity(user);
		String password = HashFunction.hashStringSHA256(user.getPassword());
		userEntity.setPassword(password);
		userEntity.setDate(LocalDate.now());
		userRep.save(userEntity);
		return true;
	}

	/**
	 * This method allows the login of a registered user.
	 *
	 * @param user	UserDTO is the user to log in.
	 * @return		LoginDTO that contains the email of the logged user and the session token if the login is
	 * 				completed without problems, of null if the password is wrong.
	 */
	public LoginDTO loginUser(UserDTO user) {
		UserEntity userEntity = this.userRep.findByEmail(user.getEmail());
		String hashedPassword = HashFunction.hashStringSHA256(user.getPassword());
		if(userEntity.getPassword().equals(hashedPassword)) {
			LoginDTO login = new LoginDTO();
			login.setEmail(userEntity.getEmail());
			String secretKey = "inglouriousBasterds";
			String token = Jwts.builder().setSubject(user.getEmail()).claim("role", "USER").setIssuedAt(new Date())
						   .signWith(SignatureAlgorithm.HS256, secretKey).compact();
			login.setToken(token);
			return login;
		}	
		return null;
	}

	/**
	 * This method allows to check if a email is already registered or not.
	 *
	 * @param email		String is the email to check.
	 * @return			Boolean that is true if the email is already registered, false instead.
	 */
	public boolean isRegistered(String email) {
		return !(this.userRep.findByEmail(email) == null);
	}

	/**
	 * This method allows to retrieve all the registered users.
	 *
	 * @return	List of UserEntities.
	 */
	public List<UserEntity> getAllUsers() {
		List<UserEntity> entities = this.userRep.findAll();
		return entities;
	} 

}
