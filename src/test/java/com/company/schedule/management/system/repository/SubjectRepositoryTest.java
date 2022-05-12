package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.repository.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
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
class SubjectRepositoryTest extends BaseIntegrationTest {

    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void create_shouldReturnCorrectSubject_whenInputCorrectData() {
        Subject expected = new Subject(1L, null, null);
        Subject actual = subjectRepository.saveAndFlush(new Subject(null, null));
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectSubject_whenInputExistId() {
        assertEquals(TEST_SUBJECT, subjectRepository.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentSubjectId() {
        assertEquals(Optional.empty(), subjectRepository.findById(10000L));
    }

    @Test
    void findAll_shouldReturnSubjectsList() {
        List<Subject> subjects = subjectRepository.findAll();
        assertFalse(subjects.isEmpty());
    }

    @Test
    void update_shouldUpdateSubject_whenInputExistId() {
        Subject actual = subjectRepository.saveAndFlush(TEST_SUBJECT);
        assertEquals(TEST_SUBJECT, actual);
    }

    @Test
    void delete_shouldDeleteSubject_whenInputExistId() {
        if(subjectRepository.findById(TEST_SUBJECT.getId()).isPresent()){
            subjectRepository.deleteById(TEST_SUBJECT.getId());
        }

        assertTrue(subjectRepository.findById(TEST_SUBJECT.getId()).isEmpty());
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistSubjectId() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                subjectRepository.deleteById(100L));
    }
}