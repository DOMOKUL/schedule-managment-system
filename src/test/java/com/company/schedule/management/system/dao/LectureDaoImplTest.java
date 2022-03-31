package com.company.schedule.management.system.dao;

import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.dao.impl.LectureDaoImpl;
import com.company.schedule.management.system.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

    private static final Audience TEST_AUDIENCE = new Audience(10L, 10, 10, null);
    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);
    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Group TEST_GROUP = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);

    private static final Lecture TEST_LECTURE = new Lecture(10L, 10, Date.valueOf("1988-09-29"),
            TEST_AUDIENCE, TEST_GROUP,
            new Lesson(10L, 10, LocalTime.of(13, 0, 0),
                    Duration.ofMinutes(90L), TEST_SUBJECT, null),
            new Teacher(10L, TEST_FACULTY, null));

    private static final Lesson TEST_LESSON = new Lesson(10L, 10, LocalTime.of(13, 0, 0),
            Duration.ofMinutes(90L), TEST_SUBJECT, null);

    private static final Teacher TEST_TEACHER = new Teacher(10L, TEST_FACULTY, null);

    @Autowired
    private LectureDaoImpl lectureDao;

    @Test
    void create_shouldReturnCorrectLecture_whenInputCorrectData() {
        Lecture actual = lectureDao.create(new Lecture(10, Date.valueOf("2019-01-26"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 1, LocalTime.of(13, 0, 0),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null)));
        Lecture expected = new Lecture(1L, 10, Date.valueOf("2019-01-26"), TEST_AUDIENCE, TEST_GROUP,
                TEST_LESSON, TEST_TEACHER);
        assertEquals(expected, actual);
        //same
    }

    @Test
    void create_shouldThrowException_whenInputExistId() {
        Lecture testLecture = new Lecture(10L, 10, Date.valueOf("1988-09-29"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 10, LocalTime.of(13, 0, 0),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null));
        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                lectureDao.create(testLecture));
    }

    @Test
    void findById_shouldReturnCorrectLecture_whenInputExistId() {
        assertEquals(TEST_LECTURE, lectureDao.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentLectureId() {
        assertThrows(DaoException.class, () ->
                lectureDao.findById(10000L));
    }

    @Test
    void findAll_shouldReturnLecturesList() {
        List<Lecture> lectures = lectureDao.findAll();
        assertFalse(lectures.isEmpty());
    }

    @Test
    void update_shouldUpdateLecture_whenInputExistId() {
        Lecture actual = lectureDao.update(TEST_LECTURE);
        assertEquals(TEST_LECTURE, actual);
    }

    @Test
    void delete_shouldDeleteLecture_whenInputExistId() {
        boolean actual = lectureDao.deleteById(TEST_LECTURE.getId());
        assertTrue(actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistLectureId() {
        assertThrows(DaoException.class, () ->
                lectureDao.deleteById(100L));
    }
}