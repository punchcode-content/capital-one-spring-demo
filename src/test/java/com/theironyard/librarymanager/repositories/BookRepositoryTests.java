package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.entities.Publisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookRepositoryTests {
    @Autowired private BookRepository bookRepository;
    @Autowired private PublisherRepository publisherRepository;

    @Test
    public void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertNotNull(books);
        assertTrue(books.isEmpty());

        Book book = new Book();
        book.setTitle("Book");
        bookRepository.save(book);

        books = bookRepository.findAll();
        assertNotNull(books);
        assertTrue(!books.isEmpty());

    }

    @Test
    public void testCanRetrieveSavedBook() {
        Book book = new Book();
        book.setTitle("Book");
        book = bookRepository.save(book);

        Book retrievedBook = bookRepository.findOne(book.getId());
        assertThat(retrievedBook, notNullValue());
        assertThat(retrievedBook.getId(), equalTo(book.getId()));
        assertThat(retrievedBook.getTitle(), equalTo("Book"));
    }

    @Test
    public void testRetrieveBookPublisher() {
        Book book = new Book();
        book.setTitle("Book");
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisher = publisherRepository.save(publisher);
        book.setPublisher(publisher);

        book = bookRepository.save(book);

        Book retrievedBook = bookRepository.findOne(book.getId());
        assertThat(retrievedBook, notNullValue());
        assertThat(retrievedBook.getId(), equalTo(book.getId()));
        assertThat(retrievedBook.getTitle(), equalTo("Book"));

        assertThat(retrievedBook.getPublisher(), notNullValue());
        assertThat(retrievedBook.getPublisher().getId(), notNullValue());
        assertThat(retrievedBook.getPublisher().getName(), equalTo("Publisher"));
    }
}
