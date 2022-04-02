package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.LectureDao;
import com.company.schedule.managment.system.dao.exception.DaoException;
import com.company.schedule.managment.system.model.Lecture;
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
public class LectureDaoImpl implements LectureDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LectureDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Lecture create(Lecture lecture) {
        try {
            SimpleJdbcInsert insertLecture = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("lectures")
                    .usingGeneratedKeyColumns("id");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("date", lecture.getDate());
            parameters.put("number", lecture.getNumber());
            parameters.put("audience_id", lecture.getAudience().getId());
            parameters.put("group_id", lecture.getGroup().getId());
            parameters.put("lesson_id", lecture.getLesson().getId());
            parameters.put("teacher_id", lecture.getTeacher().getId());
            Number newId = insertLecture.executeAndReturnKey(parameters);
            lecture.setId(newId.longValue());
            return new Lecture(newId.longValue(), lecture.getNumber(), lecture.getDate(), lecture.getAudience(),
                    lecture.getGroup(), lecture.getLesson(), lecture.getTeacher());
        } catch (Exception cause) {
            throw new DaoException("Lecture with id: " + lecture.getId() + " already exist", cause);
        }
    }

    @Override
    public Lecture findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM lectures WHERE id=?", new Object[]{id},
                    new BeanPropertyRowMapper<>(Lecture.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Lecture with id: " + id + " doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public List<Lecture> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM lectures", new BeanPropertyRowMapper<>(Lecture.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Lectures doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public boolean update(Lecture lecture) {
        var updateRowCount = jdbcTemplate.update("UPDATE lectures SET date=?,number=?,audience_id=?,group_id=?,lesson_id=?,teacher_id=? WHERE id=?",
                lecture.getDate(), lecture.getNumber(), lecture.getAudience().getId(), lecture.getGroup().getId(),
                lecture.getLesson().getId(), lecture.getTeacher().getId(), lecture.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM lectures WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
