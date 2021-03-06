package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.model.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FacultyDaoImplTest extends BaseIntegrationTest {

    private static final List<Group> TEST_GROUP_LIST = List.of(new Group("BSBO-04-20",
            new Faculty(2L, "IKBSA", null, null),
            List.of(new Student(2L, 2, null, null)),
            List.of(new Lecture(3L, 2, Date.valueOf("2019-01-26"), null, null, null, null))));
    private static final List<Teacher> TEST_TEACHER_LIST = List.of(new Teacher(
            new Faculty(3L, "IKBSC", null, null), null));
    private final FacultyDaoImpl facultyDao = new FacultyDaoImpl(DATA_SOURCE);

    @Test
    void create_shouldReturnCorrectFaculty_whenInputCorrectData() {
        Faculty testFaculty = new Faculty("IT", TEST_GROUP_LIST, TEST_TEACHER_LIST);
        facultyDao.delete(1L);
        facultyDao.create(testFaculty);
        assertEquals(2L, facultyDao.create(testFaculty).getId());
    }

    @Test
    void findById_shouldReturnCorrectFaculty_whenInputExistId() {
        Faculty testFaculty = new Faculty(1L, "IKBSP", null, null);
        assertEquals(testFaculty, facultyDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnFacultiesList() {
        List<Faculty> faculties = facultyDao.findAll();
        assertFalse(faculties.isEmpty());
    }

    @Test
    void update_shouldUpdateFaculty_whenInputExistId() {
        Faculty testFaculty = new Faculty(1L, "IT", TEST_GROUP_LIST, TEST_TEACHER_LIST);
        boolean actual = facultyDao.update(testFaculty);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteFaculty_whenInputExistId() {
        boolean actual = facultyDao.delete(1L);
        assertTrue(actual);
    }
}