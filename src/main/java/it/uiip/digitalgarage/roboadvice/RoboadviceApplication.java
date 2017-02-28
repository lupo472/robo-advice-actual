package it.uiip.digitalgarage.roboadvice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class RoboadviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoboadviceApplication.class, args);
	}
}
