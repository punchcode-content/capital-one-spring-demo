package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Author;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapAuthorService implements AuthorService {
    private Map<Integer, Author> authors;

    public MapAuthorService() {
        authors = new HashMap<>();
    }

    @Override
    public List<Author> listAll() {
        return new ArrayList<>(authors.values());
    }

    @Override
    public Author getById(Integer id) {
        return authors.get(id);
    }

    @Override
    public Author saveOrUpdate(Author author) {
        if (author != null) {
            if (author.getId() == null) {
                author.setId(getNextKey());
            }
            authors.put(author.getId(), author);

            return author;
        } else {
            throw new RuntimeException("Author cannot be null");
        }
    }

    @Override
    public Author deleteById(Integer id) {
        Author author = authors.get(id);
        authors.remove(id);
        return author;
    }

    private Integer getNextKey() {
        if (authors.keySet().isEmpty()) {
            return 1;
        } else {
            return Collections.max(authors.keySet()) + 1;
        }
    }
}
