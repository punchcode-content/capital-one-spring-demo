package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.services.BookService;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookService bookService;
    private PublisherService publisherService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
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
        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherService.listAll());
        return "books/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherService.listAll());
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
