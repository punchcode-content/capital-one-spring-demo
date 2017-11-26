package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
