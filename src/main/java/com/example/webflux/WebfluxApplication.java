package com.example.webflux;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class WebfluxApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
    public void run(ApplicationArguments arg0) throws Exception {

        log.info("\n\t********************************************"
                +"\n\t***         WebfluxDemo  0.0.2           ***"
                +"\n\t********************************************");

	}

}
