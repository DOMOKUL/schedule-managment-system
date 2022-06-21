package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.repository.TeacherRepository;
import com.company.schedule.management.system.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TeacherServiceImpl.class})
class TeacherServiceImplTest {

    private static final Faculty TEST_FACULTY = new Faculty(1L, "BASO", null, null);

    @Autowired
    TeacherServiceImpl teacherServiceImpl;
    @MockBean
    TeacherRepository teacherRepository;

    private Teacher teacherWithId;
    private Teacher teacherWithoutId;
    private List<Teacher> teacherList;

    @BeforeEach
    void setUp() {
        teacherWithId = new Teacher(1L, TEST_FACULTY, null);
        teacherWithoutId = new Teacher(TEST_FACULTY, null);
        teacherList = List.of(teacherWithId);
    }

    @Test
    void saveTeacher_shouldReturnTeacher_whenInputCorrectValue() {
        when(teacherRepository.saveAndFlush(teacherWithoutId)).thenReturn(teacherWithId);
        teacherServiceImpl.saveTeacher(teacherWithoutId);
        verify(teacherRepository, times(1)).saveAndFlush(teacherWithoutId);
    }

    @Test
    void saveTeacher_shouldThrowException_whenInputExistTeacher() {
        when(teacherRepository.saveAndFlush(teacherWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.saveTeacher(teacherWithId));
    }

    @Test
    void getTeacherById_shouldReturnTeacher_whenInputExistId() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.ofNullable(teacherWithId));
        assertThat(teacherServiceImpl.getTeacherById(teacherWithId.getId())).isEqualTo(teacherWithId);
    }

    @Test
    void getTeacherById_shouldThrowException_whenInputNonExistTeacher() {
        when(teacherRepository.findById(teacherWithId.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> teacherServiceImpl.getTeacherById(teacherWithId.getId()));
    }

    @Test
    void getAllTeachers_shouldReturnListTeachers() {
        when(teacherRepository.findAll()).thenReturn(teacherList);
        List<Teacher> teachers = teacherServiceImpl.getAllTeachers();

        assertEquals(teacherList, teachers);
        verify(teacherRepository, times(2)).findAll();
    }

    @Test
    void updateSubject_shouldReturnUpdatedSubject_whenInputCorrectSubject() {
        Teacher expected = new Teacher(1L, TEST_FACULTY, null);

        when(teacherRepository.saveAndFlush(expected)).thenReturn(new Teacher(1L, TEST_FACULTY, null));
        Teacher actual = teacherServiceImpl.updateTeacher(expected);

        assertEquals(expected, actual);
        verify(teacherRepository, times(1)).saveAndFlush(expected);
    }

    @Test
    void updateSubject_shouldThrowException_whenInputNonExistSubject() {
        when(teacherRepository.saveAndFlush(teacherWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> teacherServiceImpl.updateTeacher(teacherWithId));
    }

    @Test
    void deleteSubjectById_shouldReturnTrue_whenInputExistSubjectId() {
        when(teacherRepository.findById(teacherWithId.getId())).thenReturn(Optional.of(teacherWithId));

        teacherServiceImpl.deleteTeacherById(teacherWithId.getId());

        verify(teacherRepository, times(1)).deleteById(teacherWithId.getId());
    }
}