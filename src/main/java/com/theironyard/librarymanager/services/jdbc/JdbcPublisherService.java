package com.theironyard.librarymanager.services.jdbc;

import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.services.PublisherService;
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
public class JdbcPublisherService implements PublisherService {
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publisher> listAll() {
        return jdbcTemplate.query("SELECT * FROM publishers", new PublisherRowMapper());
    }

    @Override
    public Publisher getById(Integer id) {
        try {
            return jdbcTemplate
                    .queryForObject("SELECT * FROM publishers WHERE id=?", new Object[]{id}, new PublisherRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Publisher saveOrUpdate(Publisher publisher) {
        if (publisher.getId() != null) {
            return update(publisher);
        }

        return create(publisher);
    }

    private Publisher create(Publisher publisher) {
        String sql = "INSERT INTO publishers(name) VALUES(?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, publisher.getName());
            return ps;
        }, holder);
        Integer newPublisherId = holder.getKey().intValue();
        publisher.setId(newPublisherId);
        return publisher;
    }

    private Publisher update(Publisher publisher) {
        jdbcTemplate.update("UPDATE publishers SET name = ? WHERE id = ?", publisher.getName(), publisher.getId());
        return publisher;
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM publishers WHERE id = ?", id);
    }

    class PublisherRowMapper implements RowMapper<Publisher> {
        @Override
        public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
            Publisher publisher = new Publisher();
            publisher.setId(rs.getInt("id"));
            publisher.setName(rs.getString("name"));
            return publisher;
        }
    }
}


