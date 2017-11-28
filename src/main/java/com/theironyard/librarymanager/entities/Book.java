package com.theironyard.librarymanager.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1, message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @Pattern(regexp = "^|978-\\d{10}$", message = "ISBN must be 13 digits in the format 978-XXXXXXXXXX")
    @Column(unique = true)
    private String isbn;

    private Integer yearPublished;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    static Book withTitle(String title) {
        Book book = new Book();
        book.setTitle(title);
        return book;
    }

    public String getAuthorsString() {
        if (authors == null || authors.isEmpty()) {
            return "";
        }

        return String.join(", ",
                authors.stream()
                       .filter(Objects::nonNull)
                       .map(Author::getName)
                       .collect(Collectors.toList()));
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

    public List<Author> getAuthors() {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
        publisher.addBook(this);
    }

    public String getUrl() {
        if (getId() != null) {
            return "/books/" + getId();
        }
        return null;
    }

    public String getImageUrl() {
        if (getIsbn() != null) {
            return "http://covers.openlibrary.org/b/isbn/" + getIsbn() + "-M.jpg";
        }
        return null;
    }

    public void addAuthor(Author author) {
        this.getAuthors().add(author);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Book && id != null && id.equals(((Book) o).id));
    }
}
