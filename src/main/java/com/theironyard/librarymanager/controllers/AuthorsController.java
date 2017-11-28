package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.repositories.AuthorRepository;
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
    private AuthorRepository repository;

    @Autowired
    public void setRepository(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "")
    public String index(Model model) {
        List<Author> authors = repository.findAll();
        model.addAttribute("authors", authors);
        return "authors/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "authors/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Author author = repository.findOne(id);
        model.addAttribute("author", author);
        return "authors/show";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Author author = repository.findOne(id);
        model.addAttribute("author", author);
        return "authors/form";
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "authors/form";
        }

        repository.save(author);
        return "redirect:/authors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        repository.delete(id);
        return "redirect:/authors";
    }
}
