package com.example.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.webflux.handler.BookHandler;
import com.example.webflux.handler.HelloWorldHandler;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routeHelloWorld(HelloWorldHandler helloWorldHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/helloWorld")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloWorldHandler::helloWorld);
    }

    @Bean
    public RouterFunction<ServerResponse> routeHelloWorldStream(HelloWorldHandler helloWorldHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/helloWorldStream")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloWorldHandler::helloWorldStream);
    }

    @Bean
    public RouterFunction<ServerResponse> routeBook(BookHandler bookHandler) {
        return RouterFunctions.route()
                .GET("/books/{id}", RequestPredicates.accept(MediaType.TEXT_PLAIN), bookHandler::getBookById)
                .GET("books", RequestPredicates.accept(MediaType.TEXT_PLAIN), bookHandler::getBooks)
                .POST("/books/create", RequestPredicates.contentType(MediaType.APPLICATION_JSON),
                        bookHandler::createBook)
                .build();
    }

    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")            
            .maxAge(3600);
    }

}
