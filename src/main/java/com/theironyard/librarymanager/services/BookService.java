package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN') || hasRole('EDITOR')")
    public Book save(Book book) {
        return repository.save(book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer id) {
        repository.delete(id);
    }
}
