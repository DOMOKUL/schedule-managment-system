package com.company.schedule.management.system.dao;

import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.dao.impl.TeacherDaoImpl;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class TeacherDaoImplTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Teacher TEST_TEACHER = new Teacher(10L, TEST_FACULTY, null);

    @Autowired
    private TeacherDaoImpl teacherDao;

    @Test
    void create_shouldReturnCorrectTeacher_whenInputCorrectData() {
        Teacher expected = new Teacher(1L, TEST_FACULTY, null);
        Teacher actual = teacherDao.create(new Teacher(TEST_FACULTY, null));
        assertEquals(expected, actual);
    }

    @Test
    void create_shouldThrowException_whenInputExistId() {
        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                teacherDao.create(TEST_TEACHER));
    }

    @Test
    void findById_shouldReturnCorrectTeacher_whenInputExistId() {
        assertEquals(TEST_TEACHER, teacherDao.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentTeacherId() {
        assertEquals(Optional.empty(), teacherDao.findById(10000L));
    }

    @Test
    void findAll_shouldReturnTeachersList() {
        List<Teacher> teachers = teacherDao.findAll();
        assertFalse(teachers.isEmpty());
    }

    @Test
    void update_shouldUpdateTeacher_whenInputExistId() {
        Teacher actual = teacherDao.update(TEST_TEACHER);
        assertEquals(TEST_TEACHER, actual);
    }

    @Test
    void delete_shouldDeleteTeacher_whenInputExistId() {
        boolean actual = teacherDao.deleteById(TEST_TEACHER.getId());
        assertTrue(actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistTeacherId() {
        assertThrows(DaoException.class, () ->
                teacherDao.deleteById(100L));
    }
}