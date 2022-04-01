package com.company.schedule.management.system.service;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.FacultyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FacultyServiceImpl.class})
class FacultyServiceImplTest {

    @Autowired
    private FacultyServiceImpl facultyServiceImpl;

    @MockBean
    private FacultyDao facultyDao;

    private Faculty facultyWithId;
    private Faculty facultyWithoutId;
    private List<Faculty> facultyList;

    @BeforeEach
    void setUp() {
        facultyWithId = new Faculty(1L, "IKBSP", null, null);
        facultyWithoutId = new Faculty("IKBSP", null, null);
        facultyList = List.of(facultyWithId);
    }

    @Test
    void saveFaculty_shouldReturnNewFaculty_whenInputCorrectData() {
        when(facultyDao.create(facultyWithoutId)).thenReturn(facultyWithId);
        facultyServiceImpl.saveFaculty(facultyWithoutId);
        verify(facultyDao, times(1)).create(facultyWithoutId);
    }

    @Test
    void saveFaculty_shouldThrowException_whenInputExistFaculty() {
        when(facultyDao.create(facultyWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> facultyServiceImpl.saveFaculty(facultyWithId));
    }

    @Test
    void getFacultyById_shouldReturnFaculty_whenInputExistId() {
        when(facultyDao.findById(1L)).thenReturn(Optional.ofNullable(facultyWithId));
        assertThat(facultyServiceImpl.getFacultyById(facultyWithId.getId())).isEqualTo(facultyWithId);
    }

    @Test
    void getFacultyById_shouldThrowException_whenInputNonExistFacultyId() {
        when(facultyDao.findById(facultyWithId.getId())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> facultyServiceImpl.getFacultyById(facultyWithId.getId()));
    }

    @Test
    void getAllFaculties_shouldReturnListFaculties() {
        when(facultyDao.findAll()).thenReturn(facultyList);
        List<Faculty> faculties = facultyServiceImpl.getAllFaculties();

        assertEquals(facultyList, faculties);
        verify(facultyDao, times(1)).findAll();
    }

    @Test
    void updateFaculty_shouldReturnUpdatedFaculty_whenInputExistFaculty() {
        Faculty expected = new Faculty(1L, "IT", null, null);

        when(facultyDao.update(expected)).thenReturn(expected);
        Faculty actual = facultyServiceImpl.updateFaculty(expected);

        assertEquals(expected, actual);
        verify(facultyDao, times(1)).update(expected);
    }

    @Test
    void updateFaculty_shouldThrowException_whenInputNonExistFaculty() {
        when(facultyDao.update(facultyWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> facultyServiceImpl.updateFaculty(facultyWithId));
    }

    @Test
    void deleteFacultyById_shouldReturnTrue_whenInputExistFaculty() {
        when(facultyDao.deleteById(facultyWithId.getId())).thenReturn(true);

        assertTrue(facultyServiceImpl.deleteFacultyById(facultyWithId.getId()));

        verify(facultyDao, times(1)).deleteById(facultyWithId.getId());
    }

    @Test
    void deleteFacultyById_shouldThrowException_whenInputNonExistFacultyId() {
        when(facultyDao.deleteById(facultyWithId.getId())).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> facultyServiceImpl.deleteFacultyById(facultyWithId.getId()));
    }
}