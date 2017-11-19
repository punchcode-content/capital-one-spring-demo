package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/authors")
@RestController
public class AuthorsController {
    private AuthorService authorService;

    @Autowired
    private void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/")
    public List<Author> index() {
        return authorService.listAllAuthors();
    }
}
