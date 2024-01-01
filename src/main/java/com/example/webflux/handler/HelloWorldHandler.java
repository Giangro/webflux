package com.example.webflux.handler;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class HelloWorldHandler {

	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue("Hello World from Webflux Demo v0.0.4"));
	}

	public Mono<ServerResponse> helloWorldStream(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(
						Flux.range(1, 1000)
								.window(100)
								.switchOnFirst((signal, flux) -> {									
										return signal
											.get()
											.concatWith(
												flux.skip(1)													
													.delayElements(Duration.ofMillis(5000))
													.flatMap(it->it.parallel(4).runOn(Schedulers.parallel()))
											);
								})
								.flatMap(i -> {									
									return Mono.zip(
										Mono.just(i)
										,WebClient.create("http://localhost:8080")									
											.get()
											.uri("/helloWorld")
											.retrieve()
											.bodyToMono(String.class)
										,(a,b)->String.format("%d: %s",a,b));
								})
								.map(finalstr->{
									return String.format(
										"from %s: %s\n",Thread.currentThread().getName()
										,finalstr);										
								}),
						String.class));
	}

}
