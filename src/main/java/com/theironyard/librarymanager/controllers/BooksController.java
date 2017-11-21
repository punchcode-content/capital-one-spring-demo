package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("")
    public String index(Model model) {
        List<Book> books = bookService.listAll();
        model.addAttribute("books", books);
        return "books/index";
    }
}
