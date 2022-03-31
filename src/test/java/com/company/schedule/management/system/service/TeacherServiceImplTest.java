package com.company.schedule.management.system.service;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.TeacherDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.SubjectServiceImpl;
import com.company.schedule.management.system.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TeacherServiceImpl.class})
class TeacherServiceImplTest {

    private static final Faculty TEST_FACULTY = new Faculty(1L, "BASO", null, null);

    @Autowired
    TeacherServiceImpl teacherServiceImpl;

    @MockBean
    TeacherDao teacherDao;

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
        when(teacherDao.create(teacherWithoutId)).thenReturn(teacherWithId);
        teacherServiceImpl.saveTeacher(teacherWithoutId);
        verify(teacherDao, times(1)).create(teacherWithoutId);
    }

    @Test
    void saveTeacher_shouldThrowException_whenInputExistTeacher() {
        when(teacherDao.create(teacherWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> teacherServiceImpl.saveTeacher(teacherWithId));
    }

    @Test
    void getTeacherById_shouldReturnTeacher_whenInputExistId() {
        when(teacherDao.findById(1L)).thenReturn(Optional.ofNullable(teacherWithId));
        assertThat(teacherServiceImpl.getTeacherById(teacherWithId.getId())).isEqualTo(teacherWithId);
    }

    @Test
    void getTeacherById_shouldThrowException_whenInputNonExistTeacher() {
        when(teacherDao.findById(teacherWithId.getId())).thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> teacherServiceImpl.getTeacherById(teacherWithId.getId()));
    }

    @Test
    void getAllTeachers_shouldReturnListTeachers() {
        when(teacherDao.findAll()).thenReturn(teacherList);
        List<Teacher> teachers = teacherServiceImpl.getAllTeachers();

        assertEquals(teacherList, teachers);
        verify(teacherDao, times(1)).findAll();
    }

    @Test
    void updateSubject_shouldReturnUpdatedSubject_whenInputCorrectSubject() {
        Teacher expected = new Teacher(1L, TEST_FACULTY, null);

        when(teacherDao.update(expected)).thenReturn(new Teacher(1L,TEST_FACULTY,null));
        Teacher actual = teacherServiceImpl.updateTeacher(expected);

        assertEquals(expected, actual);
        verify(teacherDao, times(1)).update(expected);
    }

    @Test
    void updateSubject_shouldThrowException_whenInputNonExistSubject() {
        when(teacherDao.update(teacherWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> teacherServiceImpl.updateTeacher(teacherWithId));
    }

    @Test
    void deleteSubjectById_shouldReturnTrue_whenInputExistSubjectId() {
        when(teacherDao.deleteById(teacherWithId.getId())).thenReturn(true);

        assertTrue(teacherServiceImpl.deleteTeacherById(teacherWithId.getId()));

        verify(teacherDao, times(1)).deleteById(teacherWithId.getId());
    }

    @Test
    void deleteSubjectById_shouldThrowException_whenInputNonExistSubjectId() {
        when(teacherDao.deleteById(teacherWithId.getId())).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> teacherServiceImpl.deleteTeacherById(teacherWithId.getId()));
    }
}