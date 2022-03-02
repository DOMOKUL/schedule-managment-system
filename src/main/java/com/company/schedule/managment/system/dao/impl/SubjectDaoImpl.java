package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.SubjectDao;
import com.company.schedule.managment.system.models.Subject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectDaoImpl implements SubjectDao {

    private final JdbcTemplate jdbcTemplate;

    public SubjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Subject create(Subject subject) {
        jdbcTemplate.update("INSERT INTO subjects VALUES (?,?)", subject.getId(), subject.getName());
        return subject;
    }

    @Override
    public Subject findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM subjects WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Subject.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Subject> findAll() {
        return jdbcTemplate.query("SELECT * From subjects", new BeanPropertyRowMapper<>(Subject.class));
    }

    @Override
    public boolean update(Subject subject) {
        var updateRowCount = jdbcTemplate.update("UPDATE subjects SET name=? WHERE id=?",
                subject.getName(), subject.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM subjects WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
