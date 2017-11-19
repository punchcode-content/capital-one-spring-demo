package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Author;

import java.util.List;

public interface AuthorService {
    List<Author> listAllAuthors();

    void createSampleAuthors();
}
