package com.theironyard.librarymanager.listeners;

import com.theironyard.librarymanager.services.AuthorService;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    private AuthorService authorService;
    private PublisherService publisherService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        authorService.createSampleAuthors();
        System.out.println("Sample authors loaded");

        publisherService.createSamplePublishers();
        System.out.println("Sample publishers loaded");
    }
}
