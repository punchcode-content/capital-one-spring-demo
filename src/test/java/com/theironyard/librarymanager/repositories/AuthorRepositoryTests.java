package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Author;
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
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthorRepositoryTests {
    @Autowired private AuthorRepository authorRepo;

    @Test
    public void testIdAutomaticallyAssigned() {
        Author author = new Author();
        author.setName("Author");
        Author savedAuthor = authorRepo.save(author);

        assertNotNull(savedAuthor.getId());
    }

    @Test
    public void testCanRetrieveSavedAuthor() {
        Author author = new Author();
        author.setName("Author");
        author = authorRepo.save(author);

        Author retrievedAuthor = authorRepo.findOne(author.getId());
        assertThat(retrievedAuthor, notNullValue());
        assertThat(retrievedAuthor.getId(), equalTo(author.getId()));
        assertThat(retrievedAuthor.getName(), equalTo("Author"));
    }

    @Test
    public void testFindAll() {
        List<Author> authors = authorRepo.findAll();
        assertNotNull(authors);
        assertTrue(authors.isEmpty());

        Author author = new Author();
        author.setName("Author");
        authorRepo.save(author);

        authors = authorRepo.findAll();
        assertNotNull(authors);
        assertTrue(!authors.isEmpty());
    }

    @Test
    public void testCanUpdateAuthor() {
        // Set up -- save a new author
        Author author = new Author();
        author.setName("Author");
        author = authorRepo.save(author);
        author = authorRepo.findOne(author.getId());

        author.setName("New name");
        author = authorRepo.save(author);
        Author retrievedAuthor = authorRepo.findOne(author.getId());
        assertThat(retrievedAuthor.getName(), equalTo("New name"));
    }

    @Test
    public void testCanDeleteAuthor() {
        // Set up -- save a new author
        Author author = new Author();
        author.setName("Author");
        author = authorRepo.save(author);

        authorRepo.delete(author.getId());
        Author retrievedAuthor = authorRepo.findOne(author.getId());
        assertNull(retrievedAuthor);
    }
}
