package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findOneByName(String name);
}
