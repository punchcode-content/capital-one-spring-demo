package com.theironyard.librarymanager.controllers.api;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.repositories.BookRepository;
import com.theironyard.librarymanager.responses.ApiBooksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class ApiBooksController {
    private BookRepository bookRepository;

    @Autowired
    private void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    public ApiBooksResponse index(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer perPage) {

        if (perPage > 50) {
            return new ApiBooksResponse("error", "perPage cannot be more than 50");
        }

        Page<Book> books;
        PageRequest pageRequest = new PageRequest(page, perPage);

        books = bookRepository.findAll(pageRequest);
        return new ApiBooksResponse(books);
    }
}
