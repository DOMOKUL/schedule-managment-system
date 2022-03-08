package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.TeacherDao;
import com.company.schedule.managment.system.dao.exception.DaoException;
import com.company.schedule.managment.system.model.Teacher;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher create(Teacher teacher) {
        try {
            SimpleJdbcInsert insertTeacher = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("teachers")
                    .usingGeneratedKeyColumns("id");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("first_name", teacher.getFirstName());
            parameters.put("last_name", teacher.getLastName());
            parameters.put("middle_name", teacher.getMiddleName());
            parameters.put("faculty_id", teacher.getFaculty().getId());
            Number newId = insertTeacher.executeAndReturnKey(parameters);
            teacher.setId(newId.longValue());
            return new Teacher(newId.longValue(), teacher.getFaculty(), teacher.getLectures());
        } catch (Exception cause) {
            throw new DaoException("Teacher with id: " + teacher.getId() + " already exist", cause);
        }
    }

    @Override
    public Teacher findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM teachers WHERE id=?", new Object[]{id},
                    new BeanPropertyRowMapper<>(Teacher.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Teacher with id: " + id + " doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public List<Teacher> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM teachers", new BeanPropertyRowMapper<>(Teacher.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Teacher doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public boolean update(Teacher teacher) {
        var updateRowCount = jdbcTemplate.update("UPDATE teachers SET first_name=?, last_name=?, middle_name=?, faculty_id=? WHERE id=?",
                teacher.getFirstName(), teacher.getLastName(), teacher.getMiddleName(),
                teacher.getFaculty().getId(), teacher.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM teachers WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
