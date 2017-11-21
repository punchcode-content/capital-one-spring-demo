package com.theironyard.librarymanager.entities;

import java.util.Set;
import java.util.stream.Collectors;

public class Book {
    private Integer id;
    private String title;
    private String isbn;
    private Integer yearPublished;
    private Set<Author> authors;
    private Publisher publisher;

    public String getAuthorsString() {
        if (authors == null || authors.isEmpty()) {
            return "";
        }

        return String.join(", ", authors.stream().map(Author::getName).collect(Collectors.toList()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Integer yearPublished) {
        this.yearPublished = yearPublished;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
