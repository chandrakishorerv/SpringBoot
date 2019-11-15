package org.bridgelabz.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication

public class SpringBootMain {
	public static void main(String[] args) {

		SpringApplication.run(SpringBootMain.class, args);
	}
}
