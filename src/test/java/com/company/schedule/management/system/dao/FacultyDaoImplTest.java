package com.company.schedule.management.system.dao;

import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.dao.impl.FacultyDaoImpl;
import com.company.schedule.management.system.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class FacultyDaoImplTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, null, null, null);

    @Autowired
    private FacultyDaoImpl facultyDao;

    @Test
    void create_shouldReturnCorrectFaculty_whenInputCorrectData() {
        Faculty actual = facultyDao.create(new Faculty(null, null, null));
        Faculty expected = new Faculty(1L, null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    void create_shouldThrowException_whenInputExistId() {
        Faculty testFaculty = new Faculty("IKBSP", null, null);
        assertThrows(DataIntegrityViolationException.class, () ->
                facultyDao.create(testFaculty));
    }

    @Test
    void findById_shouldReturnCorrectFaculty_whenInputExistId() {
        Faculty testFaculty = new Faculty(10L, "IKBSP", null, null);
        assertEquals(testFaculty, facultyDao.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentFacultyId() {
        assertThrows(DaoException.class, () ->
                facultyDao.findById(10000L));
    }

    @Test
    void findAll_shouldReturnFacultiesList() {
        List<Faculty> faculties = facultyDao.findAll();
        assertFalse(faculties.isEmpty());
    }

    @Test
    void update_shouldUpdateFaculty_whenInputExistId() {
        Faculty actual = facultyDao.update(TEST_FACULTY);
        assertEquals(TEST_FACULTY, actual);
    }

    @Test
    void update_shouldThrowDaoException_whenInputNotExistFacultyId() {
        Faculty testFaculty = new Faculty("IKBSP", null, null);
        assertThrows(DaoException.class, () ->
                facultyDao.update(testFaculty));
    }

    @Test
    void delete_shouldDeleteFaculty_whenInputExistId() {
        boolean actual = facultyDao.deleteById(TEST_FACULTY.getId());
        assertTrue(actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistFacultyId() {
        assertThrows(DaoException.class, () ->
                facultyDao.deleteById(100L));
    }
}