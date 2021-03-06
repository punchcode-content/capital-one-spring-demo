package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class PublisherFormatter implements Formatter<Publisher> {
    private PublisherRepository publisherRepository;

    @Autowired
    public void setPublisherRepository(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher parse(String text, Locale locale) throws ParseException {
        Integer id = Integer.valueOf(text);
        return publisherRepository.findOne(id);
    }

    @Override
    public String print(Publisher object, Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }
}
