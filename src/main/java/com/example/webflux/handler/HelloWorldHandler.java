package com.example.webflux.handler;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.webflux.dto.BookDto;
import com.example.webflux.model.Book;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HelloWorldHandler {

	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue("Hello World from Webflux Demo v0.0.5"));
	}

	public Mono<ServerResponse> helloWorldStream(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromPublisher(
						Flux.range(1, 1000)
								.window(100)
								.switchOnFirst((signal, flux) -> {
									return signal
											.get()
											.concatWith(
													flux.skip(1)
															.delayElements(Duration.ofMillis(100))
															.flatMap(it -> it));
								})
								.flatMap(i -> {
									BookDto newbook = new BookDto(String.format("Libro #%d",i), "Libro di Alessandro", false);
									return WebClient.create("http://localhost:8080")
											.post()
											.uri("/books/create")
											.contentType(MediaType.APPLICATION_JSON)
											.body(BodyInserters.fromValue(newbook))
											.retrieve()
											.bodyToMono(Book.class);											
								})
						,Book.class));
	}

}
