package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByUsername(String username);
}
