package com.company.schedule.management.system.dao.mapper;

import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Subject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Lesson lesson = new Lesson();

        lesson.setId(resultSet.getLong("id"));
        lesson.setDuration(Duration.ofMillis(resultSet.getLong("duration")));
        lesson.setNumber(resultSet.getInt("number"));
        lesson.setStartTime(resultSet.getTime("start_time").toLocalTime());
        lesson.setSubject(new Subject(resultSet.getLong("subject_id"), null));

        return lesson;
    }
}
