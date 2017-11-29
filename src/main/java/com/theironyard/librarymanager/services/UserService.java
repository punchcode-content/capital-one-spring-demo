package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.User;
import com.theironyard.librarymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        if (user.getPassword() != null) {
            user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
        }
        User newUser = userRepository.save(user);
        return newUser;
    }
}
