package com.theironyard.librarymanager.controllers.api;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.repositories.BookRepository;
import com.theironyard.librarymanager.responses.ApiBooksResponse;
import com.theironyard.librarymanager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class ApiBooksController {
    private BookRepository bookRepository;
    private BookService bookService;

    @Autowired
    private void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        Book newBook = bookService.save(book);
        return newBook;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }
}
