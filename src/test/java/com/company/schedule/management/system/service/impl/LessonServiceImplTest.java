package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        lessonWithId = new Lesson(1L, 1, LocalTime.of(10, 0, 0),
                Duration.ofMinutes(90), TEST_SUBJECT, null);
        lessonWithoutId = new Lesson(1, LocalTime.of(10, 0, 0),
                Duration.ofMinutes(90), TEST_SUBJECT, null);
        lessonList = List.of(lessonWithId);
    }

    @AfterEach
    public void tearDown() {
        lessonWithId = lessonWithoutId = null;
        lessonList = null;
    }

    @Test
    void saveLesson() {
        when(lessonDao.create(lessonWithoutId)).thenReturn(lessonWithId);
        lessonServiceImpl.saveLesson(lessonWithoutId);
        verify(lessonDao, times(1)).create(lessonWithoutId);
    }

    @Test
    void getLessonById() {
        when(lessonDao.findById(1L)).thenReturn(Optional.ofNullable(lessonWithId));
        assertThat(lessonServiceImpl.getLessonById(lessonWithId.getId())).isEqualTo(lessonWithId);
    }

    @Test
    void getAllLessons() {
        when(lessonDao.findAll()).thenReturn(lessonList);
        List<Lesson> lessons = lessonServiceImpl.getAllLessons();

        assertEquals(lessonList, lessons);
        verify(lessonDao, times(1)).findAll();
    }

    @Test
    void updateLesson() {
        Lesson expected = new Lesson(1L, 2, LocalTime.of(13, 0, 0),
                Duration.ofMinutes(90), TEST_SUBJECT, null);

        when(lessonDao.update(expected)).thenReturn(expected);
        Lesson actual = lessonServiceImpl.updateLesson(expected);

        assertEquals(expected, actual);
        verify(lessonDao, times(1)).update(expected);
    }

    @Test
    void deleteLessonById() {
        when(lessonDao.deleteById(lessonWithId.getId())).thenReturn(true);

        assertTrue(lessonServiceImpl.deleteLessonById(lessonWithId.getId()));

        verify(lessonDao, times(1)).deleteById(lessonWithId.getId());
    }
}