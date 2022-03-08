package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.models.Faculty;
import com.company.schedule.managment.system.models.Group;
import com.company.schedule.managment.system.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class StudentDaoImplTest {

    private static final Student TEST_STUDENT = new Student(1,
            new Group(1L, "BABO-02-12", null, null, null),
            new Faculty(1L, "IFGPA", null, null));

    private static final Student TEST_STUDENT_WITH_ID = new Student(1L,1,
            new Group(1L, "BABO-02-12", null, null, null),
            new Faculty(1L, "IFGPA", null, null));

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");
    private StudentDaoImpl studentDao;


    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        studentDao = new StudentDaoImpl(dataSource);
        container.start();
    }

    @Test
    void create_shouldReturnCorrectStudent_whenInputCorrectData() {
        studentDao.delete(1L);
        assertEquals(1L, studentDao.create(TEST_STUDENT).getId());
    }

    @Test
    void findById_shouldReturnCorrectStudent_whenInputExistId() {
        Student expected = new Student(1L, 1, null, null);
        assertEquals(expected, studentDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnStudentsList() {
        List<Student> students = studentDao.findAll();
        assertFalse(students.isEmpty());
    }

    @Test
    void update_shouldUpdateStudent_whenInputExistId() {
        boolean actual = studentDao.update(TEST_STUDENT_WITH_ID);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteStudent_whenInputExistId() {
        boolean actual = studentDao.delete(1L);
        assertTrue(actual);
    }
}