package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    private AuthorService authorService;

    @Autowired
    private void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        List<Author> authors = authorService.listAll();
        model.addAttribute("authors", authors);
        return "authors/index";
    }

    @RequestMapping(value = "/new")
    public String newForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "authors/form";
    }

    @RequestMapping(value = "/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Author author = authorService.getById(id);
        model.addAttribute("author", author);
        return "authors/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveOrUpdate(Author author) {
        authorService.saveOrUpdate(author);
        return "redirect:/authors";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable Integer id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }
}
