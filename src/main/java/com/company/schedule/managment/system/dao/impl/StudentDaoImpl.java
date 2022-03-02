package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.StudentDao;
import com.company.schedule.managment.system.models.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDaoImpl implements StudentDao {

    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student create(Student student) {
        jdbcTemplate.update("INSERT INTO students VALUES (?,?,?,?,?,?,?)",
                student.getId(), student.getFirstName(), student.getLastName(), student.getMiddleName(),
                student.getCourseNumber(), student.getFaculty(), student.getGroup());
        return student;
    }

    @Override
    public Student findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM students WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Student.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT * From students", new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public boolean update(Student student) {
        var updateRowCount = jdbcTemplate.update("UPDATE students SET course_number=?, first_name=?, last_name=?," +
                        " middle_name=?, faculty_id=?, group_id=? WHERE id=?",
                student.getCourseNumber(), student.getFirstName(), student.getLastName(), student.getMiddleName(),
                student.getFaculty(), student.getGroup(), student.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM students WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
