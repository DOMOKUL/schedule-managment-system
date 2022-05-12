package com.company.schedule.management.system.service;

import com.company.schedule.management.system.repository.FacultyRepository;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.FacultyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FacultyServiceImpl.class})
class FacultyServiceImplTest {

    @Autowired
    private FacultyServiceImpl facultyServiceImpl;
    @MockBean
    private FacultyRepository facultyRepository;

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
        when(facultyRepository.saveAndFlush(facultyWithoutId)).thenReturn(facultyWithId);
        facultyServiceImpl.saveFaculty(facultyWithoutId);
        verify(facultyRepository, times(1)).saveAndFlush(facultyWithoutId);
    }

    @Test
    void saveFaculty_shouldThrowException_whenInputExistFaculty() {
        when(facultyRepository.saveAndFlush(facultyWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> facultyServiceImpl.saveFaculty(facultyWithId));
    }

    @Test
    void getFacultyById_shouldReturnFaculty_whenInputExistId() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.ofNullable(facultyWithId));
        assertThat(facultyServiceImpl.getFacultyById(facultyWithId.getId())).isEqualTo(facultyWithId);
    }

    @Test
    void getFacultyById_shouldThrowException_whenInputNonExistFacultyId() {
        when(facultyRepository.findById(facultyWithId.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> facultyServiceImpl.getFacultyById(facultyWithId.getId()));
    }

    @Test
    void getAllFaculties_shouldReturnListFaculties() {
        when(facultyRepository.findAll()).thenReturn(facultyList);
        List<Faculty> faculties = facultyServiceImpl.getAllFaculties();

        assertEquals(facultyList, faculties);
        verify(facultyRepository, times(2)).findAll();
    }

    @Test
    void updateFaculty_shouldReturnUpdatedFaculty_whenInputExistFaculty() {
        Faculty expected = new Faculty(1L, "IT", null, null);

        when(facultyRepository.saveAndFlush(expected)).thenReturn(expected);
        Faculty actual = facultyServiceImpl.updateFaculty(expected);

        assertEquals(expected, actual);
        verify(facultyRepository, times(1)).saveAndFlush(expected);
    }

    @Test
    void updateFaculty_shouldThrowException_whenInputNonExistFaculty() {
        when(facultyRepository.saveAndFlush(facultyWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> facultyServiceImpl.updateFaculty(facultyWithId));
    }

    @Test
    void deleteFacultyById_shouldReturnTrue_whenInputExistFaculty() {
        when(facultyRepository.findById(facultyWithId.getId())).thenReturn(Optional.of(facultyWithId));

        facultyServiceImpl.deleteFacultyById(facultyWithId.getId());

        verify(facultyRepository, times(1)).deleteById(facultyWithId.getId());
    }
}