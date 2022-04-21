package com.company.schedule.management.system.service;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {LessonServiceImpl.class})
class LessonServiceImplTest {

    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);

    @Autowired
    LessonServiceImpl lessonServiceImpl;

    @MockBean
    LessonDao lessonDao;

    private Lesson lessonWithId;
    private Lesson lessonWithoutId;
    private List<Lesson> lessonList;


    @BeforeEach
    void setUp() {
        lessonWithId = new Lesson(1L, 1, Time.valueOf(LocalTime.of(13, 0, 0)),
                Duration.ofMinutes(90), TEST_SUBJECT, null);
        lessonWithoutId = new Lesson(1, Time.valueOf(LocalTime.of(13, 0, 0)),
                Duration.ofMinutes(90), TEST_SUBJECT, null);
        lessonList = List.of(lessonWithId);
    }

    @Test
    void saveLesson_shouldReturnLesson_whenInputCorrectValue() {
        when(lessonDao.create(lessonWithoutId)).thenReturn(lessonWithId);
        lessonServiceImpl.saveLesson(lessonWithoutId);
        verify(lessonDao, times(1)).create(lessonWithoutId);
    }

    @Test
    void saveLesson_shouldThrowException_whenInputExistLesson() {
        when(lessonDao.create(lessonWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> lessonServiceImpl.saveLesson(lessonWithId));
    }

    @Test
    void getLessonById_shouldReturnLesson_whenInputExistId() {
        when(lessonDao.findById(1L)).thenReturn(Optional.ofNullable(lessonWithId));
        assertThat(lessonServiceImpl.getLessonById(lessonWithId.getId())).isEqualTo(lessonWithId);
    }

    @Test
    void getLessonById_shouldThrowException_whenInputNonExistLesson() {
        when(lessonDao.findById(lessonWithId.getId())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> lessonServiceImpl.getLessonById(lessonWithId.getId()));

    }

    @Test
    void getAllLessons_shouldReturnListLessons() {
        when(lessonDao.findAll()).thenReturn(lessonList);
        List<Lesson> lessons = lessonServiceImpl.getAllLessons();

        assertEquals(lessonList, lessons);
        verify(lessonDao, times(1)).findAll();
    }

    @Test
    void updateLesson_shouldReturnUpdatedLesson_whenInputCorrectLesson() {
        Lesson expected = new Lesson(1L, 2, Time.valueOf(LocalTime.of(13, 0, 0)),
                Duration.ofMinutes(90), TEST_SUBJECT, null);

        when(lessonDao.update(expected)).thenReturn(expected);
        Lesson actual = lessonServiceImpl.updateLesson(expected);

        assertEquals(expected, actual);
        verify(lessonDao, times(1)).update(expected);
    }

    @Test
    void updateLesson_shouldThrowException_whenInputNonExistLesson() {
        when(lessonDao.update(lessonWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> lessonServiceImpl.updateLesson(lessonWithId));
    }

    @Test
    void deleteLessonById_shouldReturnTrue_whenInputExistLessonId() {
        when(lessonDao.deleteById(lessonWithId.getId())).thenReturn(true);

        assertTrue(lessonServiceImpl.deleteLessonById(lessonWithId.getId()));

        verify(lessonDao, times(1)).deleteById(lessonWithId.getId());
    }

    @Test
    void deleteLessonById_shouldThrowException_whenInputNonExistLessonId() {
        when(lessonDao.deleteById(lessonWithId.getId())).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> lessonServiceImpl.deleteLessonById(lessonWithId.getId()));
    }
}