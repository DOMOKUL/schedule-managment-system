package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Subject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectDaoImplTest extends BaseIntegrationTest {

    private static final Subject TEST_SUBJECT = new Subject("math");
    private static final Subject TEST_SUBJECT_WITH_ID = new Subject(1L, "math");
    private final SubjectDaoImpl subjectDao = new SubjectDaoImpl(DATA_SOURCE);

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