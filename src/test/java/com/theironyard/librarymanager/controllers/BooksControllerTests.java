package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BooksController.class)
public class BooksControllerTests {
    @Autowired private MockMvc mvc;

    @MockBean private BookService bookService;
    @MockBean private PublisherService publisherService;
    @MockBean private AuthorService authorService;
    @MockBean private PublisherFormatter publisherFormatter;
    @MockBean private AuthorFormatter authorFormatter;

    @Test
    public void testIndex() throws Exception {
        Book book1 = new Book();
        Book book2 = new Book();
        given(bookService.listAll()).willReturn(Arrays.asList(book1, book2));

        mvc.perform(get("/books")
                .accept(MediaType.TEXT_HTML))
           .andExpect(status().isOk())
           .andExpect(view().name("books/index"))
           .andExpect(model().attribute("books", hasSize(2)));

        verify(bookService, times(1)).listAll();
    }

    @Captor
    private ArgumentCaptor<Book> captor;

    @Test
    public void testCreateBook() throws Exception {
        mvc.perform(post("/books")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.TEXT_HTML)
                .param("title", "Test Book")
                .param("isbn", "978-0000000000"))
           .andExpect(status().isFound());

        verify(bookService, times(1)).saveOrUpdate(captor.capture());

        Book book = captor.getValue();
        assertThat(book.getTitle(), equalTo("Test Book"));
        assertThat(book.getIsbn(), equalTo("978-0000000000"));
    }

    @Test
    public void testCreateBookWithPublisher() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Test Publisher");
        given(publisherFormatter.parse(eq("1"), anyObject())).willReturn(publisher);
        mvc.perform(post("/books")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.TEXT_HTML)
                .param("publisher", "1"))
           .andExpect(status().isFound());

        verify(bookService, times(1)).saveOrUpdate(captor.capture());

        Book book = captor.getValue();
        assertThat(book.getPublisher(), equalTo(publisher));
    }

    @Test
    public void testCreateBookWithAuthors() throws Exception {
        Author author1 = new Author();
        author1.setId(1);
        author1.setName("Test1");

        Author author2 = new Author();
        author2.setId(2);
        author2.setName("Test2");

        given(authorFormatter.parse(eq("1"), anyObject())).willReturn(author1);
        given(authorFormatter.parse(eq("2"), anyObject())).willReturn(author2);

        mvc.perform(post("/books")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.TEXT_HTML)
                .param("authors[0]", "1")
                .param("authors[1]", "2"))
           .andExpect(status().isFound());

        verify(bookService, times(1)).saveOrUpdate(captor.capture());

        Book book = captor.getValue();
        assertThat(book.getAuthorsString(), equalTo("Test1, Test2"));
    }
}
