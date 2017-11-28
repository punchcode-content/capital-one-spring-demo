package com.theironyard.librarymanager.listeners;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.importer.BookImporter;
import com.theironyard.librarymanager.importer.BookRecord;
import com.theironyard.librarymanager.repositories.AuthorRepository;
import com.theironyard.librarymanager.repositories.BookRepository;
import com.theironyard.librarymanager.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
public class SampleDataListener implements ApplicationListener<ContextRefreshedEvent> {
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;
    private BookRepository bookRepository;
    private BookImporter bookImporter;

    @Autowired
    public void setBookImporter(BookImporter bookImporter) {
        this.bookImporter = bookImporter;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setPublisherRepository(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Author author1 = new Author();
        author1.setName("Kathy Sierra");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("James Gosling");
        authorRepository.save(author2);

        Author author3 = new Author();
        author3.setName("Joshua Bloch");
        authorRepository.save(author3);

        Author author4 = new Author();
        author4.setName("Bert Bates");
        authorRepository.save(author4);

        System.out.println("Sample authors loaded");

        Publisher publisher1 = new Publisher();
        publisher1.setName("O'Reilly");
        publisherRepository.save(publisher1);

        Publisher publisher2 = new Publisher();
        publisher2.setName("Addison-Wesley Professional");
        publisherRepository.save(publisher2);

        System.out.println("Sample publishers loaded");

        Book book1 = new Book();
        book1.setTitle("Head First Java, 2nd Edition");
        book1.setIsbn("978-0596009205");
        book1.setYearPublished(2005);
        book1.setPublisher(publisher1);
        List<Author> book1authors = new ArrayList<>();
        book1authors.add(author1);
        book1authors.add(author4);
        book1.setAuthors(book1authors);
        bookRepository.save(book1);

        bookImporter.runImport();

        System.out.println("Sample books loaded");
    }
}
