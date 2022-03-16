package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectDaoImpl implements SubjectDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SubjectDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Subject create(Subject subject) {
        try {
            SimpleJdbcInsert insertSubject = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("subjects")
                    .usingGeneratedKeyColumns("id");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", subject.getName());
            Number newId = insertSubject.executeAndReturnKey(parameters);
            subject.setId(newId.longValue());
            return new Subject(newId.longValue(), subject.getName());
        } catch (Exception cause) {
            throw new DaoException("Subject with id: " + subject.getId() + " already exist", cause);
        }
    }

    @Override
    public Subject findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM subjects WHERE id=?", new Object[]{id},
                    new BeanPropertyRowMapper<>(Subject.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Subject with id: " + id + " doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public List<Subject> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM subjects", new BeanPropertyRowMapper<>(Subject.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Subject doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public boolean update(Subject subject) {
        var updateRowCount = jdbcTemplate.update("UPDATE subjects SET name=? WHERE id=?",
                subject.getName(), subject.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM subjects WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
