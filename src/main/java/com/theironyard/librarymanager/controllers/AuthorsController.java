package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
@ResponseBody
public class AuthorsController {
    private AuthorService authorService;

    @Autowired
    private void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Author> index() {
        return authorService.listAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Author show(@PathVariable Integer id) {
        return authorService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Author saveOrUpdate(@RequestBody Author author) {
        return authorService.saveOrUpdate(author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Author delete(@PathVariable Integer id) {
        return authorService.deleteById(id);
    }
}
