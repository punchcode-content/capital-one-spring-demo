package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublishersController {
    private PublisherService publisherService;

    @Autowired
    private void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        List<Publisher> publishers = publisherService.listAll();
        model.addAttribute("publishers", publishers);
        return "publishers/index";
    }

    @RequestMapping(value = "/new")
    public String newForm(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("publisher", publisher);
        return "publishers/form";
    }

    @RequestMapping(value = "/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Publisher publisher = publisherService.getById(id);
        model.addAttribute("publisher", publisher);
        return "publishers/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveOrUpdate(Publisher publisher) {
        publisherService.saveOrUpdate(publisher);
        return "redirect:/publishers";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable Integer id) {
        publisherService.deleteById(id);
        return "redirect:/publishers";
    }
}
