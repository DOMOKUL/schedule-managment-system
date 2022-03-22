package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class LectureDaoImplTest extends BaseIntegrationTest {

    private static final Audience TEST_AUDIENCE = new Audience(1L, null, null, null);
    private static final Subject TEST_SUBJECT = new Subject(1L, "math", null);
    private static final Faculty TEST_FACULTY = new Faculty(1L, null, null, null);
    private static final Group TEST_GROUP = new Group(10L, null, TEST_FACULTY, null, null);

    private static final Lecture TEST_LECTURE = new Lecture(10L, 1, Date.valueOf("2019-01-26"),
            TEST_AUDIENCE, TEST_GROUP,
            new Lesson(1L,1, LocalTime.of(13, 0, 0),
                    Duration.ofMinutes(90L), TEST_SUBJECT, null),
            new Teacher(1L, TEST_FACULTY, null));

    private static final Lesson TEST_LESSON = new Lesson(1L,1,LocalTime.of(13, 0, 0),
            Duration.ofMinutes(90L), TEST_SUBJECT, null);

    private static final Teacher TEST_TEACHER = new Teacher(1L, TEST_FACULTY, null);

    @Autowired
    private LectureDaoImpl lectureDao;

    @Test
    void create_shouldReturnCorrectLecture_whenInputCorrectData() {
        Lecture actual = lectureDao.create(new Lecture(1L, 1, Date.valueOf("2019-01-26"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(1L,1, LocalTime.of(13, 0, 0),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(1L, TEST_FACULTY, null)));
        Lecture expected = new Lecture(1L, 1, Date.valueOf("2019-01-26"),TEST_AUDIENCE,TEST_GROUP,
                TEST_LESSON, TEST_TEACHER);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectLecture_whenInputExistId() {
        assertEquals(TEST_LECTURE, lectureDao.findById(10L));
    }

    @Test
    void findAll_shouldReturnLecturesList() {
        List<Lecture> lectures = lectureDao.findAll();
        assertFalse(lectures.isEmpty());
    }

    @Test
    void update_shouldUpdateLecture_whenInputExistId() {
        boolean actual = lectureDao.update(TEST_LECTURE);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteLecture_whenInputExistId() {
        boolean actual = lectureDao.deleteById(10L);
        assertTrue(actual);
    }
}