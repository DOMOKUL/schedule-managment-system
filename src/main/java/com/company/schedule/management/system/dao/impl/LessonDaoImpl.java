package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.dao.mapper.LessonMapper;
import com.company.schedule.management.system.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LessonDaoImpl implements LessonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Lesson create(Lesson lesson) {
        try {
            SimpleJdbcInsert insertLesson = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("lessons")
                    .usingGeneratedKeyColumns("id");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("duration", lesson.getDuration().toMillis());
            parameters.put("number", lesson.getNumber());
            parameters.put("start_time", lesson.getStartTime());
            parameters.put("subject_id", lesson.getSubject().getId());
            Number newId = insertLesson.executeAndReturnKey(parameters);
            lesson.setId(newId.longValue());
            return new Lesson(newId.longValue(), lesson.getNumber(), lesson.getStartTime(), lesson.getDuration(),
                    lesson.getSubject(), lesson.getLectures());
        } catch (Exception cause) {
            throw new DaoException("Lesson with id: " + lesson.getId() + " already exist", cause);
        }
    }

    @Override
    public Lesson findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM lessons WHERE id=?", new Object[]{id}, new LessonMapper());
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Lesson with id: " + id + " doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public List<Lesson> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM lessons", new LessonMapper());
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Lessons doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public boolean update(Lesson lesson) {
        var updateRowCount = jdbcTemplate.update("UPDATE lessons SET duration=?, number=?, start_time=?, subject_id=? WHERE id=?",
                lesson.getDuration().toMillis(), lesson.getNumber(), lesson.getStartTime(), lesson.getSubject().getId(), lesson.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM lessons WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
