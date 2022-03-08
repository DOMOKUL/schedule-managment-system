package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.model.Lesson;
import com.company.schedule.managment.system.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class LessonDaoImplTest {

    private static final Lesson TEST_LESSON = new Lesson(Duration.ofMinutes(90L),
            1, LocalTime.of(13, 0, 0),
            new Subject(1L, "math"), null);

    private static final Lesson TEST_LESSON_WITH_ID = new Lesson(1L,
            1, LocalTime.of(13, 0, 0), Duration.ofMinutes(90L),
            new Subject(1L, "math"), null);

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");
    private LessonDaoImpl lessonDao;


    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        lessonDao = new LessonDaoImpl(dataSource);
        container.start();
    }

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