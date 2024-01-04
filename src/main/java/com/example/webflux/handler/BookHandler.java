package com.example.webflux.handler;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.webflux.model.Book;
import com.example.webflux.service.BookService;

import reactor.core.publisher.Mono;

@Component
public class BookHandler {

    private final BookService bookService;

    public BookHandler(BookService bookservice) {
        bookService = bookservice;
    }
    
    public Mono<ServerResponse> createBook(ServerRequest request) {
        return request.bodyToMono(Book.class)
            .flatMap(book -> bookService.save(book))
            .flatMap(book -> ServerResponse.created(URI.create("/books/"+book.getId()))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromValue(book)));
                                
    }

    public Mono<ServerResponse> getBookById(ServerRequest request) {
		int id = Integer.parseInt(request.pathVariable("id"));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(bookService.findById(id), Book.class));
	}

    public Mono<ServerResponse> getBooks(ServerRequest request) {		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(bookService.findAll(),Book.class));
	}

}
