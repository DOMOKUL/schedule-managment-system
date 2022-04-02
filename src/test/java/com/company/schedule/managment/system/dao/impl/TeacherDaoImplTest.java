package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.model.Faculty;
import com.company.schedule.managment.system.model.Teacher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TeacherDaoImplTest extends BaseIntegrationTest {

    private static final Teacher TEST_TEACHER = new Teacher(new Faculty(1L, "INASD", null, null), null);
    private static final Teacher TEST_TEACHER_WITH_ID = new Teacher(1L,
            new Faculty(1L, "INASD", null, null), null);
    private final TeacherDaoImpl teacherDao = new TeacherDaoImpl(DATA_SOURCE);

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