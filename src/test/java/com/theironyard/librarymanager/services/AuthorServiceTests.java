package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Author;
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
public class AuthorServiceTests {
    @Autowired private AuthorService service;

    @Test
    public void testIdAutomaticallyAssigned() {
        Author author = new Author();
        author.setName("Author");
        Author savedAuthor = service.saveOrUpdate(author);

        assertThat(savedAuthor, notNullValue());
    }

    @Test
    public void testCanRetrieveSavedAuthor() {
        Author author = new Author();
        author.setName("Author");
        author = service.saveOrUpdate(author);

        Author retrievedAuthor = service.getById(author.getId());
        assertThat(retrievedAuthor, notNullValue());
        assertThat(retrievedAuthor.getId(), equalTo(author.getId()));
    }

    @Test
    public void testCanListAll() {
        Author author = new Author();
        author.setName("A Test Author");
        author = service.saveOrUpdate(author);

        List<Author> authors = service.listAll();
        assertThat(authors, hasItem(hasProperty("id", is(author.getId()))));
    }

    @Test
    public void testCanUpdateAuthor() {
        // Set up -- save a new author
        Author author = new Author();
        author.setName("Author");
        author = service.saveOrUpdate(author);
        author = service.getById(author.getId());

        author.setName("New name");
        author = service.saveOrUpdate(author);
        Author retrievedAuthor = service.getById(author.getId());
        assertThat(retrievedAuthor.getName(), equalTo("New name"));
    }

    @Test
    public void testCanDeleteAuthor() {
        // Set up -- save a new author
        Author author = new Author();
        author.setName("Author");
        author = service.saveOrUpdate(author);

        service.deleteById(author.getId());
        Author retrievedAuthor = service.getById(author.getId());
        assertThat(retrievedAuthor, nullValue());
    }
}
