package com.theironyard.librarymanager.services.security;

import com.theironyard.librarymanager.entities.Role;
import com.theironyard.librarymanager.entities.User;
import com.theironyard.librarymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LibraryUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles()
                                 .stream()
                                 .map(Role::getName)
                                 .toArray(String[]::new);
        //        String[] userRoles = {"USER", "ADMIN"};
        return AuthorityUtils.createAuthorityList(userRoles);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getEncryptedPassword(),
                getAuthorities(user)
        );
    }
}
