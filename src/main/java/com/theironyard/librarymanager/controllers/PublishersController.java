package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.repositories.PublisherRepository;
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
@RequestMapping("/publishers")
public class PublishersController {
    private PublisherRepository repository;

    @Autowired
    private void setPublisherRepository(PublisherRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "")
    public String index(Model model) {
        List<Publisher> publishers = repository.findAll();
        model.addAttribute("publishers", publishers);
        return "publishers/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("publisher", publisher);
        return "publishers/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Publisher publisher = repository.findOne(id);
        model.addAttribute("publisher", publisher);
        return "publishers/show";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Publisher publisher = repository.findOne(id);
        model.addAttribute("publisher", publisher);
        return "publishers/form";
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid Publisher publisher, BindingResult result) {
        if (result.hasErrors()) {
            return "publishers/form";
        }

        repository.save(publisher);
        return "redirect:/publishers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        repository.delete(id);
        return "redirect:/publishers";
    }
}
