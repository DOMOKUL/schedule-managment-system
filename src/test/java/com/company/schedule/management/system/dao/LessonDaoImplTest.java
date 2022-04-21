package com.company.schedule.management.system.dao;

import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.dao.impl.LessonDaoImpl;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class LessonDaoImplTest extends BaseIntegrationTest {

    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);
    private static final Lesson TEST_LESSON = new Lesson(10L, 10, Time.valueOf(LocalTime.of(13, 0, 0)),
            Duration.ofMinutes(90L), TEST_SUBJECT, null);

    @Autowired
    private LessonDaoImpl lessonDao;

    @Test
    void create_shouldReturnCorrectLesson_whenInputCorrectData() {
        Lesson expected = new Lesson(1L, 10, null, null, TEST_SUBJECT, null);
        Lesson actual = lessonDao.create(new Lesson(10, null, null, TEST_SUBJECT, null));
        assertEquals(expected, actual);
    }

    @Test
    void create_shouldThrowException_whenInputExistId() {
        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                lessonDao.create(TEST_LESSON));
    }

    @Test
    void findById_shouldReturnCorrectLesson_whenInputExistId() {
        assertEquals(TEST_LESSON, lessonDao.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentLessonId() {
        assertEquals(Optional.empty(), lessonDao.findById(10000L));
    }

    @Test
    void findAll_shouldReturnLessonsList() {
        List<Lesson> lessons = lessonDao.findAll();
        assertFalse(lessons.isEmpty());
    }

    @Test
    void update_shouldUpdateLesson_whenInputExistId() {
        Lesson actual = lessonDao.update(TEST_LESSON);
        assertEquals(TEST_LESSON, actual);
    }

    @Test
    void delete_shouldDeleteLesson_whenInputExistId() {
        boolean actual = lessonDao.deleteById(TEST_LESSON.getId());
        assertTrue(actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistLessonId() {
        assertThrows(DaoException.class, () ->
                lessonDao.deleteById(100L));
    }
}