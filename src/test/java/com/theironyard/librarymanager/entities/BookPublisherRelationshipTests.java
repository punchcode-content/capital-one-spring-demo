package com.theironyard.librarymanager.entities;

import com.theironyard.librarymanager.repositories.PublisherRepository;
import com.theironyard.librarymanager.repositories.BookRepository;
import com.theironyard.librarymanager.repositories.PublisherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookPublisherRelationshipTests {
    @Autowired private BookRepository bookRepository;
    @Autowired private PublisherRepository publisherRepository;

    @Test
    public void testReciprocalRelationship() {
        Publisher publisher1 = Publisher.withName("Publisher 1");
        publisher1 = publisherRepository.save(publisher1);

        Book book1 = Book.withTitle("Book 1");
        Book book2 = Book.withTitle("Book 2");

        book1.setPublisher(publisher1);
        book1 = bookRepository.save(book1);

        book2.setPublisher(publisher1);
        book2 = bookRepository.save(book2);

        Book savedBook1 = bookRepository.findOne(book1.getId());
        assertEquals(savedBook1.getPublisher().getId(), publisher1.getId());

        Publisher savedPublisher1 = publisherRepository.findOne(publisher1.getId());
        assertNotNull(savedPublisher1.getBooks());
        assertFalse(savedPublisher1.getBooks().isEmpty());
        //        assertArrayEquals(savedPublisher1.getBooks().stream().map(Book::getId).toArray(),
        //                new Integer[]{book1.getId(), book2.getId()});
    }

}
