package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.services.AuthorService;
import com.theironyard.librarymanager.services.BookService;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookService bookService;
    private PublisherService publisherService;
    private AuthorService authorService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Book> books = bookService.listAll();
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Book book = new Book();
        List<Author> authors = Collections.singletonList(new Author());
        book.setAuthors(authors);
        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherService.listAll());
        model.addAttribute("authors", authorService.listAll());
        return "books/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Book book = bookService.getById(id);

        if (book.getAuthors() == null) {
            book.setAuthors(new ArrayList<>());
        }
        book.getAuthors().add(new Author());

        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherService.listAll());
        model.addAttribute("authors", authorService.listAll());
        return "books/form";
    }

    @PostMapping("")
    public String saveOrUpdate(Book book) {
        if (book.getId() == null) {
            bookService.saveOrUpdate(book);
        } else {
            Book prevBook = bookService.getById(book.getId());
            prevBook.setTitle(book.getTitle());
            prevBook.setIsbn(book.getIsbn());
            prevBook.setYearPublished(book.getYearPublished());
            prevBook.setPublisher(book.getPublisher());
            List<Author> authors = book.getAuthors().stream().filter(Objects::nonNull).collect(Collectors.toList());
            prevBook.setAuthors(authors);
            bookService.saveOrUpdate(prevBook);
        }
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}
