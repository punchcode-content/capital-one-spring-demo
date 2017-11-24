package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.entities.Publisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookServiceTests {
    @Autowired BookService bookService;
    @Autowired PublisherService publisherService;

    @Test
    public void testIdAutomaticallyAssigned() {
        Book book = new Book();
        book.setTitle("Book");
        Book savedBook = bookService.saveOrUpdate(book);

        assertThat(savedBook, notNullValue());
    }

    @Test
    public void testCanRetrieveSavedBook() {
        Book book = new Book();
        book.setTitle("Book");
        book = bookService.saveOrUpdate(book);

        Book retrievedBook = bookService.getById(book.getId());
        assertThat(retrievedBook, notNullValue());
        assertThat(retrievedBook.getId(), equalTo(book.getId()));
    }

    @Test
    public void testCanListAll() {
        Book book = new Book();
        book.setTitle("Book");
        book = bookService.saveOrUpdate(book);

        List<Book> books = bookService.listAll();
        assertThat(books, hasItem(hasProperty("id", is(book.getId()))));
    }

    @Test
    public void testCanUpdateBook() {
        Book book = new Book();
        book.setTitle("Book");
        book = bookService.saveOrUpdate(book);

        book.setTitle("New title");
        book = bookService.saveOrUpdate(book);
        Book retrievedBook = bookService.getById(book.getId());
        assertThat(retrievedBook.getTitle(), equalTo("New title"));
    }

    @Test
    public void testCanDeleteBook() {
        Book book = new Book();
        book.setTitle("Book");
        book = bookService.saveOrUpdate(book);

        bookService.deleteById(book.getId());
        Book retrievedBook = bookService.getById(book.getId());
        assertThat(retrievedBook, nullValue());
    }

    @Test
    public void testCanSetPublisher() {
        Publisher pub = new Publisher();
        pub.setName("Pub");
        pub = publisherService.saveOrUpdate(pub);

        Book book = new Book();
        book.setTitle("Book");
        book.setPublisher(pub);
        book = bookService.saveOrUpdate(book);

        Book retrievedBook = bookService.getById(book.getId());
        assertThat(retrievedBook.getPublisher(), notNullValue());
        assertThat(retrievedBook.getPublisher().getId(), is(pub.getId()));
    }

    @Test
    public void testCanSetPublisherToNull() {
        Publisher pub = new Publisher();
        pub.setName("Pub");
        pub = publisherService.saveOrUpdate(pub);

        Book book = new Book();
        book.setTitle("Book");
        book.setPublisher(pub);
        book = bookService.saveOrUpdate(book);

        book.setPublisher(null);
        book = bookService.saveOrUpdate(book);

        Book retrievedBook = bookService.getById(book.getId());
        assertThat(retrievedBook.getPublisher(), nullValue());
    }
}
