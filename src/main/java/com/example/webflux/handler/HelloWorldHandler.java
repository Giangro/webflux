package com.example.webflux.handler;

import java.time.Duration;
import java.util.concurrent.Executors;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class HelloWorldHandler {

	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue("Hello World from Webflux Demo v0.0.2"));
	}

	public Mono<ServerResponse> helloWorldStream(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(
						Flux.range(1, 100000)						
							.window(1000)
							.delayElements(Duration.ofSeconds(30))
							.flatMap(it->it.parallel())
							.map(i -> {
								return String.format("#%d-%s Hello World from Webflux Demo v0.0.2\n",i,Thread.currentThread().getName() );
							})							
						,String.class));
	}

}
