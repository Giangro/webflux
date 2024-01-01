package com.example.webflux.model;

import org.springframework.data.annotation.Id;

public class Book {

    @Id
    private int id;
    private String title;
    private String description;
    private boolean published;

    public Book() {
    }

    public Book(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    // getters and setters

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }
    
}
