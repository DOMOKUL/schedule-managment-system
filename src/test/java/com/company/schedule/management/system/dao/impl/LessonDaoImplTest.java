package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Subject;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LessonDaoImplTest extends BaseIntegrationTest {

    private static final Lesson TEST_LESSON = new Lesson(Duration.ofMinutes(90L),
            1, LocalTime.of(13, 0, 0),
            new Subject(1L, "math"), null);
    private static final Lesson TEST_LESSON_WITH_ID = new Lesson(1L,
            1, LocalTime.of(13, 0, 0), Duration.ofMinutes(90L),
            new Subject(1L, "math"), null);
    private LessonDaoImpl lessonDao;

    @Test
    void create_shouldReturnCorrectLesson_whenInputCorrectData() {
        lessonDao.delete(1L);
        assertEquals(1L, lessonDao.create(TEST_LESSON).getId());
    }

    @Test
    void findById_shouldReturnCorrectLesson_whenInputExistId() {
        Lesson expected = new Lesson(1L, 1,
                LocalTime.of(13, 0, 0),
                Duration.ofMillis(5400000000000L),
                new Subject(1L, null),
                null);
        assertEquals(expected, lessonDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnLessonsList() {
        List<Lesson> lessons = lessonDao.findAll();
        assertFalse(lessons.isEmpty());
    }

    @Test
    void update_shouldUpdateLesson_whenInputExistId() {
        boolean actual = lessonDao.update(TEST_LESSON_WITH_ID);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteLesson_whenInputExistId() {
        boolean actual = lessonDao.delete(1L);
        assertTrue(actual);
    }
}