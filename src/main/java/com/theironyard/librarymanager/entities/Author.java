package com.theironyard.librarymanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1, message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Book> books;

    static Author withName(String name) {
        Author author = new Author();
        author.setName(name);
        return author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getUrl() {
        if (getId() != null) {
            return "/authors/" + getId();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Author && id != null && id.equals(((Author) o).id));
    }
}
