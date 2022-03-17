package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest extends BaseIntegrationTest {

    private static final Student TEST_STUDENT = new Student(1,
            new Group(1L, "BABO-02-12", null, null, null),
            new Faculty(1L, "IFGPA", null, null));
    private static final Student TEST_STUDENT_WITH_ID = new Student(1L, 1,
            new Group(1L, "BABO-02-12", null, null, null),
            new Faculty(1L, "IFGPA", null, null));
    private StudentDaoImpl studentDao;

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