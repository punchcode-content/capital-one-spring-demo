package com.theironyard.librarymanager.entities;

import com.theironyard.librarymanager.repositories.AuthorRepository;
import com.theironyard.librarymanager.repositories.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookAuthorRelationshipTests {
    @Autowired private BookRepository bookRepository;
    @Autowired private AuthorRepository authorRepository;

    @Test
    public void testReciprocalRelationship() {
        Author author1 = Author.withName("Author 1");
        Author author2 = Author.withName("Author 2");
        author1 = authorRepository.save(author1);
        author2 = authorRepository.save(author2);

        Book book1 = Book.withTitle("Book 1");
        Book book2 = Book.withTitle("Book 2");

        book1.setAuthors(Arrays.asList(author1, author2));
        book1 = bookRepository.save(book1);

        book2.setAuthors(Arrays.asList(author1));
        book2 = bookRepository.save(book2);

        Author savedAuthor1 = authorRepository.findOne(author1.getId());
        assertNotNull(savedAuthor1.getBooks());
        assertFalse(savedAuthor1.getBooks().isEmpty());
//        assertArrayEquals(savedAuthor1.getBooks().stream().map(Book::getId).toArray(),
//                new Integer[]{book1.getId(), book2.getId()});
    }
}
