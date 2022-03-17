package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TeacherDaoImplTest extends BaseIntegrationTest {

    private static final Teacher TEST_TEACHER = new Teacher(new Faculty(1L, "INASD", null, null), null);
    private static final Teacher TEST_TEACHER_WITH_ID = new Teacher(1L,
            new Faculty(1L, "INASD", null, null), null);
    private TeacherDaoImpl teacherDao;

    @Test
    void create_shouldReturnCorrectTeacher_whenInputCorrectData() {
        teacherDao.delete(1L);
        Assertions.assertEquals(1L, teacherDao.create(TEST_TEACHER).getId());
    }

    @Test
    void findById_shouldReturnCorrectTeacher_whenInputExistId() {
        Assertions.assertEquals(new Teacher(1L, null, null), teacherDao.findById(1L));
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