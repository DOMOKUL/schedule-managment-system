package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.LectureDao;
import com.company.schedule.managment.system.models.Lecture;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LectureDaoImpl implements LectureDao {

    private final JdbcTemplate jdbcTemplate;

    public LectureDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Lecture create(Lecture lecture) {
        jdbcTemplate.update("INSERT INTO lectures VALUES (?,?,?,?,?,?,?)",
                lecture.getId(), lecture.getDate(), lecture.getNumber(),
                lecture.getAudience(), lecture.getGroup(), lecture.getLesson(), lecture.getTeacher());
        return lecture;
    }

    @Override
    public Lecture findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM lectures WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Lecture.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Lecture> findAll() {
        return jdbcTemplate.query("SELECT * From lectures", new BeanPropertyRowMapper<>(Lecture.class));
    }

    @Override
    public boolean update(Lecture lecture) {
        var updateRowCount = jdbcTemplate.update("UPDATE lectures SET date=?, number=?, audience_id=?, group_id=?," +
                        " lesson_id=?, teacher_id=? WHERE id=?",
                lecture.getDate(), lecture.getNumber(), lecture.getAudience(), lecture.getGroup(),
                lecture.getLesson(), lecture.getTeacher(), lecture.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM lectures WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
