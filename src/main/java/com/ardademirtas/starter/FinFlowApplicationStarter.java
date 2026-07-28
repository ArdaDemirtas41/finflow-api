package com.ardademirtas.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ardademirtas"})
@EnableJpaRepositories(basePackages = {"com.ardademirtas"})
@EntityScan(basePackages = {"com.ardademirtas"})
public class FinFlowApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(FinFlowApplicationStarter.class, args);
	}

}
