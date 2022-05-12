package com.company.schedule.management.system.service;

import com.company.schedule.management.system.repository.LessonRepository;
import com.company.schedule.management.system.repository.exception.DaoException;
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
import java.util.NoSuchElementException;
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
    LessonRepository lessonRepository;

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
        when(lessonRepository.saveAndFlush(lessonWithoutId)).thenReturn(lessonWithId);
        lessonServiceImpl.saveLesson(lessonWithoutId);
        verify(lessonRepository, times(1)).saveAndFlush(lessonWithoutId);
    }

    @Test
    void saveLesson_shouldThrowException_whenInputExistLesson() {
        when(lessonRepository.saveAndFlush(lessonWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> lessonServiceImpl.saveLesson(lessonWithId));
    }

    @Test
    void getLessonById_shouldReturnLesson_whenInputExistId() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.ofNullable(lessonWithId));
        assertThat(lessonServiceImpl.getLessonById(lessonWithId.getId())).isEqualTo(lessonWithId);
    }

    @Test
    void getLessonById_shouldThrowException_whenInputNonExistLesson() {
        when(lessonRepository.findById(lessonWithId.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> lessonServiceImpl.getLessonById(lessonWithId.getId()));

    }

    @Test
    void getAllLessons_shouldReturnListLessons() {
        when(lessonRepository.findAll()).thenReturn(lessonList);
        List<Lesson> lessons = lessonServiceImpl.getAllLessons();

        assertEquals(lessonList, lessons);
        verify(lessonRepository, times(2)).findAll();
    }

    @Test
    void updateLesson_shouldReturnUpdatedLesson_whenInputCorrectLesson() {
        Lesson expected = new Lesson(1L, 2, Time.valueOf(LocalTime.of(13, 0, 0)),
                Duration.ofMinutes(90), TEST_SUBJECT, null);

        when(lessonRepository.saveAndFlush(expected)).thenReturn(expected);
        Lesson actual = lessonServiceImpl.updateLesson(expected);

        assertEquals(expected, actual);
        verify(lessonRepository, times(1)).saveAndFlush(expected);
    }

    @Test
    void updateLesson_shouldThrowException_whenInputNonExistLesson() {
        when(lessonRepository.saveAndFlush(lessonWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> lessonServiceImpl.updateLesson(lessonWithId));
    }

    @Test
    void deleteLessonById_shouldReturnTrue_whenInputExistLessonId() {
        when(lessonRepository.findById(lessonWithId.getId())).thenReturn(Optional.of(lessonWithId));

        lessonServiceImpl.deleteLessonById(lessonWithId.getId());

        verify(lessonRepository, times(1)).deleteById(lessonWithId.getId());
    }
}