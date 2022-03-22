package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class SubjectDaoImplTest extends BaseIntegrationTest {

    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);

    @Autowired
    private SubjectDaoImpl subjectDao;

    @Test
    void create_shouldReturnCorrectSubject_whenInputCorrectData() {
        Subject expected = new Subject(1L, null, null);
        Subject actual = subjectDao.create(new Subject(1L, null, null));
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectSubject_whenInputExistId() {
        assertEquals(TEST_SUBJECT, subjectDao.findById(10L));
    }

    @Test
    void findAll_shouldReturnSubjectsList() {
        List<Subject> subjects = subjectDao.findAll();
        assertFalse(subjects.isEmpty());
    }

    @Test
    void update_shouldUpdateSubject_whenInputExistId() {
        boolean actual = subjectDao.update(TEST_SUBJECT);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteSubject_whenInputExistId() {
        boolean actual = subjectDao.deleteById(10L);
        assertTrue(actual);
    }
}