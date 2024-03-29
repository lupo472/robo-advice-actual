package it.uiip.digitalgarage.roboadvice.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;

/**
 * This class initialize the Spring Servlet.
 *
 * @author Luca Antilici
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RoboadviceApplication.class);
	}

}
