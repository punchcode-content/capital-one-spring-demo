package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.repositories.AuthorRepository;
import com.theironyard.librarymanager.repositories.BookRepository;
import com.theironyard.librarymanager.repositories.PublisherRepository;
import com.theironyard.librarymanager.services.AuthorService;
import com.theironyard.librarymanager.services.BookService;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookService bookService;
    private PublisherService publisherService;
    private AuthorService authorService;

    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setPublisherRepository(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Book book = new Book();
        book.setAuthors(new ArrayList<>());
        setupFormModel(model, book);
        return "books/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Book book = bookRepository.findOne(id);

        if (book.getAuthors() == null) {
            book.setAuthors(new ArrayList<>());
        }

        setupFormModel(model, book);
        return "books/form";
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setAuthors(book.getAuthors().stream().filter(Objects::nonNull).collect(Collectors.toList()));
            setupFormModel(model, book);
            return "books/form";
        }

        bookRepository.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        bookRepository.delete(id);
        return "redirect:/books";
    }

    private void setupFormModel(Model model, Book book) {
        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
    }
}
