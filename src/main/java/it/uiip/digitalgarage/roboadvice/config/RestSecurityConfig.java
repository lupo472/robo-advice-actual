package it.uiip.digitalgarage.roboadvice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//TODO this is a redundant class
@Configuration
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
  
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}
	
}
