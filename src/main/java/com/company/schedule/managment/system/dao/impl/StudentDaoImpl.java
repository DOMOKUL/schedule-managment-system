package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.StudentDao;
import com.company.schedule.managment.system.dao.exception.DaoException;
import com.company.schedule.managment.system.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class StudentDaoImpl implements StudentDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student create(Student student) {
        SimpleJdbcInsert insertStudent = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("students")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", student.getFirstName());
        parameters.put("last_name", student.getLastName());
        parameters.put("middle_name", student.getMiddleName());
        parameters.put("course_number", student.getCourseNumber());
        parameters.put("faculty_id", student.getFaculty().getId());
        parameters.put("group_id", student.getGroup().getId());
        Number newId = insertStudent.executeAndReturnKey(parameters);
        student.setId(newId.longValue());
        return new Student(newId.longValue(), student.getCourseNumber(), student.getGroup(), student.getFaculty());
    }

    @Override
    public Student findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM students WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT * FROM students", new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public boolean update(Student student) {
        var updateRowCount = jdbcTemplate.update("UPDATE students SET course_number=?, first_name=?, last_name=?," +
                        " middle_name=?, faculty_id=?, group_id=? WHERE id=?",
                student.getCourseNumber(), student.getFirstName(), student.getLastName(), student.getMiddleName(),
                student.getFaculty().getId(), student.getGroup().getId(), student.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM students WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
