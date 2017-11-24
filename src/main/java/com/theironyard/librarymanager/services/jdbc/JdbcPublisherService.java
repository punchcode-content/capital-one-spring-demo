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
import java.sql.Statement;
import java.util.List;

@Service
@Primary
public class JdbcPublisherService implements PublisherService {
    private JdbcTemplate jdbc;

    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publisher> listAll() {
        return jdbc.query("SELECT * FROM publishers", new PublisherRowMapper());
    }

    @Override
    public Publisher getById(Integer id) {
        try {
            return jdbc
                    .queryForObject("SELECT * FROM publishers WHERE id=?", new Object[]{id}, new PublisherRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Publisher saveOrUpdate(Publisher publisher) {
        if (publisher.getId() != null) {
            return this.update(publisher);
        }

        return this.create(publisher);
    }

    private Publisher create(Publisher publisher) {
        String sql = "INSERT INTO publishers(name) VALUES(?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, publisher.getName());
            return ps;
        }, holder);
        Integer newPublisherId = holder.getKey().intValue();
        publisher.setId(newPublisherId);
        return publisher;
    }

    private Publisher update(Publisher publisher) {
        jdbc.update("UPDATE publishers SET name = ? WHERE id = ?", publisher.getName(), publisher.getId());
        return publisher;
    }

    @Override
    public void deleteById(Integer id) {
        jdbc.update("DELETE FROM publishers WHERE id = ?", id);
    }
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
