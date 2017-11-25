package com.theironyard.librarymanager.services.jdbc;

import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@Primary
public class JdbcAuthorService implements AuthorService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> listAll() {
        return jdbcTemplate.query("SELECT * FROM authors", new AuthorRowMapper());
    }

    @Override
    public Author getById(Integer id) {
        try {
            return jdbcTemplate
                    .queryForObject("SELECT * FROM authors WHERE id=?", new Object[]{id}, new AuthorRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Author saveOrUpdate(Author author) {
        if (author.getId() != null) {
            return update(author);
        }

        return create(author);
    }

    private Author create(Author author) {
        String sql = "INSERT INTO authors(name) VALUES(?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, author.getName());
            return ps;
        }, holder);
        Integer newAuthorId = holder.getKey().intValue();
        author.setId(newAuthorId);
        return author;
    }

    private Author update(Author author) {
        jdbcTemplate.update("UPDATE authors SET name = ? WHERE id = ?", author.getName(), author.getId());
        return author;
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
    }

    static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setName(rs.getString("name"));
            return author;
        }
    }
}
