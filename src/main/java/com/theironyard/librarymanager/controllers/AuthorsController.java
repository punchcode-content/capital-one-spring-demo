package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    private AuthorService authorService;

    @Autowired
    private void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "")
    public String index(Model model) {
        List<Author> authors = authorService.listAll();
        model.addAttribute("authors", authors);
        return "authors/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "authors/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Author author = authorService.getById(id);
        model.addAttribute("author", author);
        return "authors/form";
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "authors/form";
        }

        authorService.saveOrUpdate(author);
        return "redirect:/authors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }
}
