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

@Component
public class HelloWorldHandler {

	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue("Hello World from Webflux Demo v0.0.4"));
	}

	public Mono<ServerResponse> helloWorldStream(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(
						Flux.range(1, 100)
								.window(10)
								.switchOnFirst((signal, flux) -> {									
										return signal
											.get()
											.concatWith(
												flux.skip(1)													
													.delayElements(Duration.ofMillis(15000))
													.flatMap(it->it)
											);
								})
								.flatMap(i -> {									
									return Mono.zip(
										Mono.just(i)
										,WebClient.create("http://ifconfig.me")									
											.get()
											.uri("/ip")
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
