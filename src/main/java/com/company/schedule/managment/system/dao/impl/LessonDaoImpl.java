package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.LessonDao;
import com.company.schedule.managment.system.dao.exception.DaoException;
import com.company.schedule.managment.system.dao.mappers.LessonMapper;
import com.company.schedule.managment.system.models.Lesson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class LessonDaoImpl implements LessonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Lesson create(Lesson lesson) {
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
    }

    @Override
    public Lesson findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM lessons WHERE id=?", new Object[]{id}, new LessonMapper());
    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query("SELECT * FROM lessons", new LessonMapper());
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
