package com.example.webflux.handler;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class HelloWorldHandler {

	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue("Hello World from Webflux Demo v0.0.3"));
	}

	public Mono<ServerResponse> helloWorldStream(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(
						Flux.range(1, 100)
								.window(11)
								.switchOnFirst((signal, flux) -> {									
										return signal
											.get()
											.concatWith(
												flux.skip(1)													
													.delayElements(Duration.ofSeconds(10))
													.flatMap(it->it.parallel())
											);
								})								
								.map(i -> {
									return String.format("#%d-%s Hello World from Webflux Demo v0.0.3\n", i,
											Thread.currentThread().getName());
								}),
						String.class));
	}

}
