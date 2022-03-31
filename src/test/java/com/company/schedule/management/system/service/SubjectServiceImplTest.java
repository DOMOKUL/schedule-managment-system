package com.company.schedule.management.system.service;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.SubjectServiceImpl;
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

@SpringBootTest(classes = {SubjectServiceImpl.class})
class SubjectServiceImplTest {

    @Autowired
    SubjectServiceImpl subjectServiceImpl;

    @MockBean
    SubjectDao subjectDao;

    private Subject subjectWithId;
    private Subject subjectWithoutId;
    private List<Subject> subjectList;

    @BeforeEach
    void setUp() {
        subjectWithId = new Subject(1L, "math", null);
        subjectWithoutId = new Subject("math", null);
        subjectList = List.of(subjectWithId);
    }

    @Test
    void saveSubject_shouldReturnSubject_whenInputCorrectValue() {
        when(subjectDao.create(subjectWithoutId)).thenReturn(subjectWithId);
        subjectServiceImpl.saveSubject(subjectWithoutId);
        verify(subjectDao, times(1)).create(subjectWithoutId);
    }

    @Test
    void saveSubject_shouldThrowException_whenInputExistSubject() {
        when(subjectDao.create(subjectWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> subjectServiceImpl.saveSubject(subjectWithId));
    }

    @Test
    void getSubjectById_shouldReturnSubject_whenInputExistId() {
        when(subjectDao.findById(1L)).thenReturn(Optional.ofNullable(subjectWithId));
        assertThat(subjectServiceImpl.getSubjectById(subjectWithId.getId())).isEqualTo(subjectWithId);
    }

    @Test
    void getStudentById_shouldThrowException_whenInputNonExistStudent() {
        when(subjectDao.findById(subjectWithId.getId())).thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> subjectServiceImpl.getSubjectById(subjectWithId.getId()));
    }

    @Test
    void getAllSubjects_shouldReturnListSubjects() {
        when(subjectDao.findAll()).thenReturn(subjectList);
        List<Subject> subjects = subjectServiceImpl.getAllSubjects();

        assertEquals(subjectList, subjects);
        verify(subjectDao, times(1)).findAll();
    }

    @Test
    void updateSubject_shouldReturnUpdatedSubject_whenInputCorrectSubject() {
        Subject expected = new Subject(1L, "art", null);

        when(subjectDao.update(expected)).thenReturn(new Subject(1L, "art", null));
        Subject actual = subjectServiceImpl.updateSubject(expected);

        assertEquals(expected, actual);
        verify(subjectDao, times(1)).update(expected);
    }

    @Test
    void updateSubject_shouldThrowException_whenInputNonExistSubject() {
        when(subjectDao.update(subjectWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> subjectServiceImpl.updateSubject(subjectWithId));
    }

    @Test
    void deleteSubjectById_shouldReturnTrue_whenInputExistSubjectId() {
        when(subjectDao.deleteById(subjectWithId.getId())).thenReturn(true);

        assertTrue(subjectServiceImpl.deleteSubjectById(subjectWithId.getId()));

        verify(subjectDao, times(1)).deleteById(subjectWithId.getId());
    }

    @Test
    void deleteSubjectById_shouldThrowException_whenInputNonExistSubjectId() {
        when(subjectDao.deleteById(subjectWithId.getId())).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> subjectServiceImpl.deleteSubjectById(subjectWithId.getId()));
    }
}