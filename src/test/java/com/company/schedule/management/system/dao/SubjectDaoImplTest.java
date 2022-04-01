package com.company.schedule.management.system.dao;

import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.dao.impl.SubjectDaoImpl;
import com.company.schedule.management.system.model.Subject;
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
class SubjectDaoImplTest extends BaseIntegrationTest {

    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);

    @Autowired
    private SubjectDaoImpl subjectDao;

    @Test
    void create_shouldReturnCorrectSubject_whenInputCorrectData() {
        Subject expected = new Subject(1L, null, null);
        Subject actual = subjectDao.create(new Subject(null, null));
        assertEquals(expected, actual);
    }

    @Test
    void create_shouldThrowException_whenInputExistId() {
        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                subjectDao.create(TEST_SUBJECT));
    }

    @Test
    void findById_shouldReturnCorrectSubject_whenInputExistId() {
        assertEquals(TEST_SUBJECT, subjectDao.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentSubjectId() {
        assertEquals(Optional.empty(), subjectDao.findById(10000L));
    }

    @Test
    void findAll_shouldReturnSubjectsList() {
        List<Subject> subjects = subjectDao.findAll();
        assertFalse(subjects.isEmpty());
    }

    @Test
    void update_shouldUpdateSubject_whenInputExistId() {
        Subject actual = subjectDao.update(TEST_SUBJECT);
        assertEquals(TEST_SUBJECT, actual);
    }

    @Test
    void delete_shouldDeleteSubject_whenInputExistId() {
        boolean actual = subjectDao.deleteById(TEST_SUBJECT.getId());
        assertTrue(actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistSubjectId() {
        assertThrows(DaoException.class, () ->
                subjectDao.deleteById(100L));
    }
}