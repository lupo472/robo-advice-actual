package it.uiip.digitalgarage.roboadvice.config;

import it.uiip.digitalgarage.roboadvice.service.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Luca on 08/03/2017.
 */

@Configuration
public class UserControllerTestConfig {

    @Bean
    public UserController userController(){
        return new UserController();
    }
}
