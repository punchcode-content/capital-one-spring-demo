package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class AuthorFormatter implements Formatter<Author> {
    private AuthorRepository authorRepository;

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author parse(String text, Locale locale) throws ParseException {
        Integer id = Integer.valueOf(text);
        return authorRepository.findOne(id);
    }

    @Override
    public String print(Author object, Locale locale) {
        if (object == null || object.getId() == null) {
            return "";
        }
        return object.getId().toString();
    }
}
