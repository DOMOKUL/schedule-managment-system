package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.models.Faculty;
import com.company.schedule.managment.system.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class TeacherDaoImplTest {

    private static final Teacher TEST_TEACHER = new Teacher(new Faculty(1L, "INASD", null, null), null);

    private static final Teacher TEST_TEACHER_WITH_ID = new Teacher(1L,
            new Faculty(1L, "INASD", null, null), null);

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");
    private TeacherDaoImpl teacherDao;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        teacherDao = new TeacherDaoImpl(dataSource);
        container.start();
    }

    @Test
    void create_shouldReturnCorrectTeacher_whenInputCorrectData() {
        teacherDao.delete(1L);
        assertEquals(1L, teacherDao.create(TEST_TEACHER).getId());
    }

    @Test
    void findById_shouldReturnCorrectTeacher_whenInputExistId() {
        assertEquals(new Teacher(1L, null, null), teacherDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnTeachersList() {
        List<Teacher> teachers = teacherDao.findAll();
        assertFalse(teachers.isEmpty());
    }

    @Test
    void update_shouldUpdateTeacher_whenInputExistId() {
        boolean actual = teacherDao.update(TEST_TEACHER_WITH_ID);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteTeacher_whenInputExistId() {
        boolean actual = teacherDao.delete(1L);
        assertTrue(actual);
    }
}