package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.FacultyDao;
import com.company.schedule.managment.system.models.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FacultyDaoImpl implements FacultyDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FacultyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Faculty create(Faculty faculty) {
        jdbcTemplate.update("INSERT INTO faculties VALUES (?,?)",
                faculty.getId(), faculty.getName());
        return faculty;
    }

    @Override
    public Faculty findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM faculties WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Faculty.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Faculty> findAll() {
        return jdbcTemplate.query("SELECT * From faculties", new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public boolean update(Faculty faculty) {
        var updateRowCount = jdbcTemplate.update("UPDATE faculties SET name=? WHERE id=?",
                faculty.getName(), faculty.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM faculties WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
