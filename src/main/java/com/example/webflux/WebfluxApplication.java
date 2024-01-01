package com.example.webflux;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = {"com.example.webflux.repository"}, basePackageClasses = {})
@Slf4j
public class WebfluxApplication implements ApplicationRunner {

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {

		log.info("\n\n\t********************************************"
				+ "\n\t***         WebfluxDemo  0.0.4           ***"
				+ "\n\t********************************************\n");

	}

}
