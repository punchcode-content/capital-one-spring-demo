package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
