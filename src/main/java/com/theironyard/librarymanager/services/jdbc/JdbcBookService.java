package com.theironyard.librarymanager.services.jdbc;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.services.BookService;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Service
@Primary
public class JdbcBookService implements BookService {
    private JdbcTemplate jdbcTemplate;
    private PublisherService publisherService;

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> listAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BookRowMapper());
    }

    @Override
    public Book getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM books WHERE id=?", new Object[]{id}, new BookRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Book saveOrUpdate(Book book) {
        if (book.getId() != null) {
            return update(book);
        }

        return create(book);
    }

    private Book create(Book book) {
        final Integer publisherId = book.getPublisher() == null ? null : book.getPublisher().getId();

        String sql = "INSERT INTO books(title, isbn, year_published, publisher_id) VALUES (?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setObject(3, book.getYearPublished(), Types.INTEGER);
            ps.setObject(4, publisherId, Types.INTEGER);
            return ps;
        }, holder);
        Integer newBookId = holder.getKey().intValue();
        book.setId(newBookId);

        setBookAuthors(book);

        return book;
    }

    private Book update(Book book) {
        Integer publisherId = null;
        if (book.getPublisher() != null) {
            publisherId = book.getPublisher().getId();
        }
        jdbcTemplate.update("UPDATE books SET title = ?, isbn = ?, year_published = ?, publisher_id = ? WHERE id = ?",
                book.getTitle(), book.getIsbn(), book.getYearPublished(), publisherId, book.getId());

        setBookAuthors(book);

        return book;
    }

    private List<Author> getAuthorsByBookId(Integer bookId) {
        return jdbcTemplate
                .query("SELECT a.id, a.name FROM authors_books ab LEFT JOIN authors a ON ab.author_id = a.id WHERE ab.book_id = ?",
                        new Object[]{bookId}, new JdbcAuthorService.AuthorRowMapper());
    }

    private void setBookAuthors(Book book) {
        jdbcTemplate.update("DELETE FROM authors_books WHERE book_id = ?", book.getId());
        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                jdbcTemplate.update("INSERT INTO authors_books(book_id, author_id) VALUES (?, ?)", book.getId(),
                        author.getId());
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setYearPublished(rs.getObject("year_published", Integer.class));
            book.setIsbn(rs.getString("isbn"));

            int publisherId = rs.getInt("publisher_id");
            if (publisherId != 0) {
                book.setPublisher(publisherService.getById(publisherId));
            }

            book.setAuthors(getAuthorsByBookId(book.getId()));

            return book;
        }
    }
}
