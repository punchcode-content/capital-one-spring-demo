package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByYearPublished(Integer yearPublished);

    Page<Book> findAllByYearPublished(Integer yearPublished, Pageable page);
}
