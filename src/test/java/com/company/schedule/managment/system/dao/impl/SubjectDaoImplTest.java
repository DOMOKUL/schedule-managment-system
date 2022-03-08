package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class SubjectDaoImplTest {

    private static final Subject TEST_SUBJECT = new Subject("math");

    private static final Subject TEST_SUBJECT_WITH_ID = new Subject(1L, "math");


    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");
    private SubjectDaoImpl subjectDao;


    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        subjectDao = new SubjectDaoImpl(dataSource);
        container.start();
    }

    @Test
    void create_shouldReturnCorrectSubject_whenInputCorrectData() {
        subjectDao.delete(1L);
        assertEquals(1L, subjectDao.create(TEST_SUBJECT).getId());
    }

    @Test
    void findById_shouldReturnCorrectSubject_whenInputExistId() {
        assertEquals(TEST_SUBJECT_WITH_ID, subjectDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnSubjectsList() {
        List<Subject> subjects = subjectDao.findAll();
        assertFalse(subjects.isEmpty());
    }

    @Test
    void update_shouldUpdateSubject_whenInputExistId() {
        boolean actual = subjectDao.update(TEST_SUBJECT_WITH_ID);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteSubject_whenInputExistId() {
        boolean actual = subjectDao.delete(1L);
        assertTrue(actual);
    }
}