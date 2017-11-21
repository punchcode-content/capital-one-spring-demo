package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> listAll();

    Publisher getById(Integer id);

    Publisher saveOrUpdate(Publisher publisher);

    Publisher deleteById(Integer id);

    void createSamplePublishers();
}
