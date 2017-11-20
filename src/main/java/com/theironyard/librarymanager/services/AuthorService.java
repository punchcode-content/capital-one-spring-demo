package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Author;

import java.util.List;

public interface AuthorService {
    List<Author> listAll();

    Author getById(Integer id);

    Author saveOrUpdate(Author author);

    Author deleteById(Integer id);

    void createSampleAuthors();
}
