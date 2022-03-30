package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.model.Subject;
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

@SpringBootTest(classes = {SubjectServiceImpl.class})
class SubjectServiceImplTest {

    @MockBean
    SubjectDao subjectDao;

    @Autowired
    SubjectServiceImpl subjectServiceImpl;

    private Subject subjectWithId;
    private Subject subjectWithoutId;
    private List<Subject> subjectList;

    @BeforeEach
    void setUp() {
        subjectWithId = new Subject(1L, "math", null);
        subjectWithoutId = new Subject("math", null);
        subjectList = List.of(subjectWithId);
    }

    @AfterEach
    public void tearDown() {
        subjectWithId = null;
        subjectList = null;
    }

    @Test
    void saveSubject() {
        when(subjectDao.create(subjectWithoutId)).thenReturn(subjectWithId);
        subjectServiceImpl.saveSubject(subjectWithoutId);
        verify(subjectDao, times(1)).create(subjectWithoutId);
    }

    @Test
    void getSubjectById() {
        when(subjectDao.findById(1L)).thenReturn(Optional.ofNullable(subjectWithId));
        assertThat(subjectServiceImpl.getSubjectById(subjectWithId.getId())).isEqualTo(subjectWithId);
    }

    @Test
    void getAllSubjects() {
        when(subjectDao.findAll()).thenReturn(subjectList);
        List<Subject> subjects = subjectServiceImpl.getAllSubjects();

        assertEquals(subjectList, subjects);
        verify(subjectDao, times(1)).findAll();
    }

    @Test
    void updateSubject() {
        Subject expected = new Subject(1L, "art", null);

        when(subjectDao.update(expected)).thenReturn(new Subject(1L, "art", null));
        Subject actual = subjectServiceImpl.updateSubject(expected);

        assertEquals(expected, actual);
        verify(subjectDao, times(1)).update(expected);
    }

    @Test
    void deleteSubjectById() {
        when(subjectDao.deleteById(subjectWithId.getId())).thenReturn(true);

        assertTrue(subjectServiceImpl.deleteSubjectById(subjectWithId.getId()));

        verify(subjectDao, times(1)).deleteById(subjectWithId.getId());
    }
}