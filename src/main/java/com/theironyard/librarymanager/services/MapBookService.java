package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapBookService implements BookService {
    private Map<Integer, Book> books;

    public MapBookService() {
        books = new HashMap<>();
    }

    @Override
    public List<Book> listAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book getById(Integer id) {
        return books.get(id);
    }

    @Override
    public Book saveOrUpdate(Book book) {
        if (book != null) {
            if (book.getId() == null) {
                book.setId(getNextKey());
            }
            books.put(book.getId(), book);

            return book;
        } else {
            throw new RuntimeException("Book cannot be null");
        }
    }

    @Override
    public Book deleteById(Integer id) {
        Book book = books.get(id);
        books.remove(id);
        return book;
    }

    private Integer getNextKey() {
        if (books.keySet().isEmpty()) {
            return 1;
        } else {
            return Collections.max(books.keySet()) + 1;
        }
    }
}
