package com.theironyard.librarymanager.listeners;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.services.AuthorService;
import com.theironyard.librarymanager.services.BookService;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SampleDataListener implements ApplicationListener<ContextRefreshedEvent> {
    private AuthorService authorService;
    private PublisherService publisherService;
    private BookService bookService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Author author1 = new Author();
        author1.setName("Kathy Sierra");
        authorService.saveOrUpdate(author1);

        Author author2 = new Author();
        author2.setName("James Gosling");
        authorService.saveOrUpdate(author2);

        Author author3 = new Author();
        author3.setName("Joshua Bloch");
        authorService.saveOrUpdate(author3);

        Author author4 = new Author();
        author4.setName("Bert Bates");
        authorService.saveOrUpdate(author4);

        System.out.println("Sample authors loaded");

        Publisher publisher1 = new Publisher();
        publisher1.setName("O'Reilly Media");
        publisherService.saveOrUpdate(publisher1);

        Publisher publisher2 = new Publisher();
        publisher2.setName("Addison-Wesley Professional");
        publisherService.saveOrUpdate(publisher2);

        System.out.println("Sample publishers loaded");

        Book book1 = new Book();
        book1.setTitle("Head First Java, 2nd Edition");
        book1.setIsbn("978-0596009205");
        book1.setYearPublished(2005);
        book1.setPublisher(publisher1);
        Set<Author> book1authors = new HashSet<>();
        book1authors.add(author1);
        book1authors.add(author4);
        book1.setAuthors(book1authors);
        bookService.saveOrUpdate(book1);

        Book book2 = new Book();
        book2.setTitle("Effective Java, 3nd Edition");
        book2.setIsbn("978-0134685991");
        book2.setYearPublished(2017);
        book2.setPublisher(publisher2);
        Set<Author> book2authors = new HashSet<>();
        book2authors.add(author3);
        book2.setAuthors(book2authors);
        bookService.saveOrUpdate(book2);

        System.out.println("Sample books loaded");
    }
}
