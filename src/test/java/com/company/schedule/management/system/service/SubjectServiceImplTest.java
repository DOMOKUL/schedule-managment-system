package com.company.schedule.management.system.service;

import com.company.schedule.management.system.repository.SubjectRepository;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.SubjectServiceImpl;
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

@SpringBootTest(classes = {SubjectServiceImpl.class})
class SubjectServiceImplTest {

    @Autowired
    SubjectServiceImpl subjectServiceImpl;
    @MockBean
    SubjectRepository subjectRepository;

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
        when(subjectRepository.saveAndFlush(subjectWithoutId)).thenReturn(subjectWithId);
        subjectServiceImpl.saveSubject(subjectWithoutId);
        verify(subjectRepository, times(1)).saveAndFlush(subjectWithoutId);
    }

    @Test
    void saveSubject_shouldThrowException_whenInputExistSubject() {
        when(subjectRepository.saveAndFlush(subjectWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> subjectServiceImpl.saveSubject(subjectWithId));
    }

    @Test
    void getSubjectById_shouldReturnSubject_whenInputExistId() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.ofNullable(subjectWithId));
        assertThat(subjectServiceImpl.getSubjectById(subjectWithId.getId())).isEqualTo(subjectWithId);
    }

    @Test
    void getStudentById_shouldThrowException_whenInputNonExistStudent() {
        when(subjectRepository.findById(subjectWithId.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> subjectServiceImpl.getSubjectById(subjectWithId.getId()));
    }

    @Test
    void getAllSubjects_shouldReturnListSubjects() {
        when(subjectRepository.findAll()).thenReturn(subjectList);
        List<Subject> subjects = subjectServiceImpl.getAllSubjects();

        assertEquals(subjectList, subjects);
        verify(subjectRepository, times(2)).findAll();
    }

    @Test
    void updateSubject_shouldReturnUpdatedSubject_whenInputCorrectSubject() {
        Subject expected = new Subject(1L, "art", null);

        when(subjectRepository.saveAndFlush(expected)).thenReturn(new Subject(1L, "art", null));
        Subject actual = subjectServiceImpl.updateSubject(expected);

        assertEquals(expected, actual);
        verify(subjectRepository, times(1)).saveAndFlush(expected);
    }

    @Test
    void updateSubject_shouldThrowException_whenInputNonExistSubject() {
        when(subjectRepository.saveAndFlush(subjectWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> subjectServiceImpl.updateSubject(subjectWithId));
    }

    @Test
    void deleteSubjectById_shouldReturnTrue_whenInputExistSubjectId() {
        when(subjectRepository.findById(subjectWithId.getId())).thenReturn(Optional.of(subjectWithId));

        subjectServiceImpl.deleteSubjectById(subjectWithId.getId());

        verify(subjectRepository, times(1)).deleteById(subjectWithId.getId());
    }
}