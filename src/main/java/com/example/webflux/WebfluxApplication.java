package com.example.webflux;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.webflux.dto.BookDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Slf4j
public class WebfluxApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {

		log.info("\n\n\t********************************************"
				+ "\n\t***         WebfluxDemo  0.0.5           ***"
				+ "\n\t********************************************\n");

	}

}
