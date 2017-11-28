package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    Publisher findOneByName(String name);
}
