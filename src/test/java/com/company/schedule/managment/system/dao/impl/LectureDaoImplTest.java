package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class LectureDaoImplTest {

    private static final Lecture TEST_LECTURE = new Lecture(
            1,
            Date.valueOf("2022-03-7"),
            new Audience(1, 10),
            new Group("BISO-07-18", null, null, null),
            new Lesson(Duration.ofMinutes(90L), 1, LocalTime.of(13, 0, 0),
                    new Subject(1L, "math"), null),
            new Teacher(new Faculty(1L, "IKBSP", null, null), null));

    private static final Lecture TEST_LECTURE_WITH_ID = new Lecture(1L,
            1,
            Date.valueOf("2022-03-7"),
            new Audience(1, 10),
            new Group("BISO-07-18", null, null, null),
            new Lesson(Duration.ofMinutes(90L), 1, LocalTime.of(13, 0, 0),
                    new Subject(1L, "math"), null),
            new Teacher(new Faculty(1L, "IKBSP", null, null), null));

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");
    private LectureDaoImpl lectureDao;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        lectureDao = new LectureDaoImpl(dataSource);
        container.start();
    }

    @Test
    void create_shouldReturnCorrectLecture_whenInputCorrectData() {
        lectureDao.delete(1L);
        assertEquals(1L, lectureDao.create(TEST_LECTURE).getId());
    }

    @Test
    void findById_shouldReturnCorrectLecture_whenInputExistId() {
        Lecture expected = new Lecture(1L, 1, Date.valueOf("1988-09-29"),
                null, null, null, null);
        assertEquals(expected, lectureDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnLecturesList() {
        List<Lecture> lectures = lectureDao.findAll();
        assertFalse(lectures.isEmpty());
    }

    @Test
    void update_shouldUpdateLecture_whenInputExistId() {
        boolean actual = lectureDao.update(TEST_LECTURE_WITH_ID);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteLecture_whenInputExistId() {
        boolean actual = lectureDao.delete(1L);
        assertTrue(actual);
    }
}