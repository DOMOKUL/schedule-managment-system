package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.model.Faculty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FacultyServiceImpl.class})
class FacultyServiceImplTest {

    @MockBean
    private FacultyDao facultyDao;

    @Autowired
    private FacultyServiceImpl facultyServiceImpl;
    private Faculty facultyWithId;
    private Faculty facultyWithoutId;
    private List<Faculty> facultyList;

    @BeforeEach
    void setUp() {
        facultyWithId = new Faculty(1L, "IKBSP", null, null);
        facultyWithoutId = new Faculty("IKBSP", null, null);
        facultyList = List.of(facultyWithId);
    }

    @AfterEach
    public void tearDown() {
        facultyWithId = facultyWithoutId = null;
        facultyList = null;
    }

    @Test
    void saveFaculty() {
        when(facultyDao.create(facultyWithoutId)).thenReturn(facultyWithId);
        facultyServiceImpl.saveFaculty(facultyWithoutId);
        verify(facultyDao, times(1)).create(facultyWithoutId);
    }

    @Test
    void getFacultyById() {
        when(facultyDao.findById(1L)).thenReturn(Optional.ofNullable(facultyWithId));
        assertThat(facultyServiceImpl.getFacultyById(facultyWithId.getId())).isEqualTo(facultyWithId);
    }

    @Test
    void getAllFaculties() {
        when(facultyDao.findAll()).thenReturn(facultyList);
        List<Faculty> faculties = facultyServiceImpl.getAllFaculties();

        assertEquals(facultyList, faculties);
        verify(facultyDao, times(1)).findAll();
    }

    @Test
    void updateFaculty() {
        Faculty expected = new Faculty(1L, "IT", null, null);

        when(facultyDao.update(expected)).thenReturn(expected);
        Faculty actual = facultyServiceImpl.updateFaculty(expected);

        assertEquals(expected, actual);
        verify(facultyDao, times(1)).update(expected);
    }

    @Test
    void deleteFacultyById() {
        when(facultyDao.deleteById(facultyWithId.getId())).thenReturn(true);

        assertTrue(facultyServiceImpl.deleteFacultyById(facultyWithId.getId()));

        verify(facultyDao, times(1)).deleteById(facultyWithId.getId());
    }
}