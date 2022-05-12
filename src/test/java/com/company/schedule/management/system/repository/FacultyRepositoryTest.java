package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class FacultyRepositoryTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, null, null, null);

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void create_shouldReturnCorrectFaculty_whenInputCorrectData() {
        Faculty actual = facultyRepository.saveAndFlush(new Faculty(null, null, null));
        Faculty expected = new Faculty(1L, null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectFaculty_whenInputExistId() {
        Faculty testFaculty = new Faculty(10L, "IKBSP", null, null);
        assertEquals(testFaculty, facultyRepository.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentFacultyId() {
        assertEquals(Optional.empty(), facultyRepository.findById(10000L));
    }

    @Test
    void findAll_shouldReturnFacultiesList() {
        List<Faculty> faculties = facultyRepository.findAll();
        assertFalse(faculties.isEmpty());
    }

    @Test
    void update_shouldUpdateFaculty_whenInputExistId() {
        Faculty actual = facultyRepository.saveAndFlush(TEST_FACULTY);
        assertEquals(TEST_FACULTY, actual);
    }

    @Test
    void delete_shouldDeleteFaculty_whenInputExistId() {
        if(facultyRepository.findById(TEST_FACULTY.getId()).isPresent()){
            facultyRepository.deleteById(TEST_FACULTY.getId());
        }

        assertTrue(facultyRepository.findById(TEST_FACULTY.getId()).isEmpty());
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistFacultyId() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                facultyRepository.deleteById(100L));
    }
}