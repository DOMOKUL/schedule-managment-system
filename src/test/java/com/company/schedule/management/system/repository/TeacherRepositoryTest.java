package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.repository.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class TeacherRepositoryTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Teacher TEST_TEACHER = new Teacher(10L, TEST_FACULTY, null);

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void create_shouldReturnCorrectTeacher_whenInputCorrectData() {
        Teacher expected = new Teacher(1L, TEST_FACULTY, null);
        Teacher actual = teacherRepository.saveAndFlush(new Teacher(TEST_FACULTY, null));
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectTeacher_whenInputExistId() {
        assertEquals(TEST_TEACHER, teacherRepository.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentTeacherId() {
        assertEquals(Optional.empty(), teacherRepository.findById(10000L));
    }

    @Test
    void findAll_shouldReturnTeachersList() {
        List<Teacher> teachers = teacherRepository.findAll();
        assertFalse(teachers.isEmpty());
    }

    @Test
    void update_shouldUpdateTeacher_whenInputExistId() {
        Teacher actual = teacherRepository.saveAndFlush(TEST_TEACHER);
        assertEquals(TEST_TEACHER, actual);
    }

    @Test
    void delete_shouldDeleteTeacher_whenInputExistId() {
        if(teacherRepository.findById(TEST_TEACHER.getId()).isPresent()){
            teacherRepository.deleteById(TEST_TEACHER.getId());
        }

        assertTrue(teacherRepository.findById(TEST_TEACHER.getId()).isEmpty());
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistTeacherId() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                teacherRepository.deleteById(100L));
    }
}