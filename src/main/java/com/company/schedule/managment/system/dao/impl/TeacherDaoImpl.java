package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.TeacherDao;
import com.company.schedule.managment.system.models.Teacher;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher create(Teacher teacher) {
        jdbcTemplate.update("INSERT INTO teachers VALUES (?,?,?,?,?)",
                teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getMiddleName(),
                teacher.getFaculty());
        return teacher;
    }

    @Override
    public Teacher findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM teachers WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Teacher.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query("SELECT * From teachers", new BeanPropertyRowMapper<>(Teacher.class));
    }

    @Override
    public boolean update(Teacher teacher) {
        var updateRowCount = jdbcTemplate.update("UPDATE teachers SET first_name=?, last_name=?," +
                        " middle_name=?, faculty_id=? WHERE id=?",
                teacher.getFirstName(), teacher.getLastName(), teacher.getMiddleName(),
                teacher.getFaculty());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM teachers WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
