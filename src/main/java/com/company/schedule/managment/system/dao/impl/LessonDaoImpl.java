package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.LessonDao;
import com.company.schedule.managment.system.models.Lesson;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonDaoImpl implements LessonDao {

    private final JdbcTemplate jdbcTemplate;

    public LessonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Lesson create(Lesson lesson) {
        jdbcTemplate.update("INSERT INTO lessons VALUES (?,?,?,?,?)",
                lesson.getId(), lesson.getDuration(), lesson.getNumber(),
                lesson.getStartTime(), lesson.getSubject());
        return lesson;
    }

    @Override
    public Lesson findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM lessons WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Lesson.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query("SELECT * From lessons", new BeanPropertyRowMapper<>(Lesson.class));
    }

    @Override
    public boolean update(Lesson lesson) {
        var updateRowCount = jdbcTemplate.update("UPDATE lessons SET duration=?, number=?, start_time=?, subject_id=? WHERE id=?",
                lesson.getDuration(), lesson.getNumber(), lesson.getStartTime(), lesson.getSubject(), lesson.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM lessons WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
