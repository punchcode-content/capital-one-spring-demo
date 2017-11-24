package com.theironyard.librarymanager.services.jdbc;

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

import java.sql.*;
import java.util.List;

@Service
@Primary
public class JdbcBookService implements BookService {
    private JdbcTemplate jdbc;
    private PublisherService publisherService;

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> listAll() {
        return jdbc.query("SELECT * FROM books", new BookRowMapper());
    }

    @Override
    public Book getById(Integer id) {
        try {
            return jdbc.queryForObject("SELECT * FROM books WHERE id=?", new Object[]{id}, new BookRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Book saveOrUpdate(Book book) {
        if (book.getId() != null) {
            return this.update(book);
        }

        return this.create(book);
    }

    private Book create(Book book) {
        final Integer publisherId = book.getPublisher() == null ? null : book.getPublisher().getId();

        String sql = "INSERT INTO books(title, isbn, year_published, publisher_id) VALUES (?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setObject(3, book.getYearPublished(), Types.INTEGER);
            ps.setObject(4, publisherId, Types.INTEGER);
            return ps;
        }, holder);
        Integer newBookId = holder.getKey().intValue();
        book.setId(newBookId);
        return book;
    }

    private Book update(Book book) {
        Integer publisherId = null;
        if (book.getPublisher() != null) {
            publisherId = book.getPublisher().getId();
        }
        jdbc.update("UPDATE books SET title = ?, isbn = ?, year_published = ?, publisher_id = ? WHERE id = ?",
                book.getTitle(), book.getIsbn(), book.getYearPublished(), publisherId, book.getId());
        return book;
    }

    @Override
    public void deleteById(Integer id) {
        jdbc.update("DELETE FROM books WHERE id = ?", id);
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

            return book;
        }
    }
}
