package com.company.schedule.management.system.service;

import com.company.schedule.management.system.dao.StudentDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.StudentServiceImpl;
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

@SpringBootTest(classes = {StudentServiceImpl.class})
class StudentServiceImplTest {

    private static final Faculty TEST_FACULTY = new Faculty(1L, "IKBSP", null, null);
    private static final Group TEST_GROUP = new Group(1L, "BSBO-04-20", TEST_FACULTY, null, null);

    @Autowired
    StudentServiceImpl studentServiceImpl;
    @MockBean
    StudentDao studentDao;

    private Student studentWithId;
    private Student studentWithoutId;
    private List<Student> studentList;

    @BeforeEach
    void setUp() {
        studentWithId = new Student(1L, 1, TEST_GROUP);
        studentWithoutId = new Student(1, TEST_GROUP);
        studentList = List.of(studentWithId);
    }

    @Test
    void saveStudent_shouldReturnStudent_whenInputCorrectValue() {
        when(studentDao.create(studentWithoutId)).thenReturn(studentWithId);
        studentServiceImpl.saveStudent(studentWithoutId);
        verify(studentDao, times(1)).create(new Student(1, TEST_GROUP));
    }

    @Test
    void saveStudent_shouldThrowException_whenInputExistStudent() {
        when(studentDao.create(studentWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> studentServiceImpl.saveStudent(studentWithId));
    }

    @Test
    void getStudentById_shouldReturnStudent_whenInputExistId() {
        when(studentDao.findById(1L)).thenReturn(Optional.ofNullable(studentWithId));
        assertThat(studentServiceImpl.getStudentById(studentWithId.getId())).isEqualTo(studentWithId);
    }

    @Test
    void getStudentById_shouldThrowException_whenInputNonExistStudent() {
        when(studentDao.findById(studentWithId.getId())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> studentServiceImpl.getStudentById(studentWithId.getId()));
    }

    @Test
    void getAllStudents_shouldReturnListStudents() {
        when(studentDao.findAll()).thenReturn(studentList);
        List<Student> students = studentServiceImpl.getAllStudents();

        assertEquals(studentList, students);
        verify(studentDao, times(1)).findAll();
    }

    @Test
    void updateStudent_shouldReturnUpdatedStudent_whenInputCorrectStudent() {
        Student expected = new Student(1L, 2, TEST_GROUP);

        when(studentDao.update(expected)).thenReturn(new Student(1L, 2, TEST_GROUP));
        Student actual = studentServiceImpl.updateStudent(expected);

        assertEquals(expected, actual);
        verify(studentDao, times(1)).update(expected);
    }

    @Test
    void updateStudent_shouldThrowException_whenInputNonExistStudent() {
        when(studentDao.update(studentWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> studentServiceImpl.updateStudent(studentWithId));
    }

    @Test
    void deleteStudentById_shouldReturnTrue_whenInputExistStudentId() {
        when(studentDao.deleteById(studentWithId.getId())).thenReturn(true);

        studentServiceImpl.deleteStudentById(studentWithId.getId());

        verify(studentDao, times(1)).deleteById(studentWithId.getId());
    }

    @Test
    void deleteLessonById_shouldThrowException_whenInputNonExistLessonId() {
        when(studentDao.deleteById(studentWithId.getId())).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> studentServiceImpl.deleteStudentById(studentWithId.getId()));
    }
}