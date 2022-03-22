package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Faculty;
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
class FacultyDaoImplTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, null, null, null);

    @Autowired
    private FacultyDaoImpl facultyDao;

    @Test
    void create_shouldReturnCorrectFaculty_whenInputCorrectData() {
        Faculty actual = facultyDao.create(new Faculty(1L, null, null, null));
        Faculty expected = new Faculty(1L, null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectFaculty_whenInputExistId() {
        Faculty testFaculty = new Faculty(10L, "IKBSP", null, null);
        assertEquals(testFaculty, facultyDao.findById(10L));
    }

    @Test
    void findAll_shouldReturnFacultiesList() {
        List<Faculty> faculties = facultyDao.findAll();
        assertFalse(faculties.isEmpty());
    }

    @Test
    void update_shouldUpdateFaculty_whenInputExistId() {
        boolean actual = facultyDao.update(TEST_FACULTY);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteFaculty_whenInputExistId() {
        boolean actual = facultyDao.deleteById(10L);
        assertTrue(actual);
    }
}