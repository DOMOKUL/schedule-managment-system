package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.LectureDao;
import com.company.schedule.management.system.model.*;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {LectureServiceImpl.class})
class LectureServiceImplTest {

    private static final Audience TEST_AUDIENCE = new Audience(10L, 10, 10, null);
    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);
    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Group TEST_GROUP = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);

    @MockBean
    LectureDao lectureDao;

    @Autowired
    LectureServiceImpl lectureServiceImpl;

    private Lecture lectureWithId;
    private Lecture lectureWithoutId;
    private List<Lecture> lectureList;

    @BeforeEach
    void setUp() {
        lectureWithId = new Lecture(1L, 10, Date.valueOf("1988-09-29"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 10, LocalTime.of(13, 0, 0),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null));
        lectureWithoutId = new Lecture(10, Date.valueOf("1988-09-29"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 10, LocalTime.of(13, 0, 0),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null));
        lectureList = List.of(lectureWithId);
    }

    @AfterEach
    public void tearDown() {
        lectureWithId = lectureWithoutId = null;
        lectureList = null;
    }

    @Test
    void saveLecture() {
        when(lectureDao.create(lectureWithoutId)).thenReturn(lectureWithId);
        lectureServiceImpl.saveLecture(lectureWithoutId);
        verify(lectureDao, times(1)).create(lectureWithoutId);
    }

    @Test
    void getLectureById() {
        when(lectureDao.findById(1L)).thenReturn(Optional.ofNullable(lectureWithId));
        assertThat(lectureServiceImpl.getLectureById(lectureWithId.getId())).isEqualTo(lectureWithId);
    }

    @Test
    void getAllLectures() {
        when(lectureDao.findAll()).thenReturn(lectureList);
        List<Lecture> lectures = lectureServiceImpl.getAllLectures();

        assertEquals(lectureList, lectures);
        verify(lectureDao, times(1)).findAll();
    }

    @Test
    void updateLecture() {
        Lecture expected = new Lecture(1L, 11, Date.valueOf("2022-09-29"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 10, LocalTime.of(13, 0, 0),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null));

        when(lectureDao.update(expected)).thenReturn(expected);
        Lecture actual = lectureServiceImpl.updateLecture(expected);

        assertEquals(expected, actual);
        verify(lectureDao, times(1)).update(expected);
    }

    @Test
    void deleteLectureById() {
        when(lectureDao.deleteById(lectureWithId.getId())).thenReturn(true);

        assertTrue(lectureServiceImpl.deleteLectureById(lectureWithId.getId()));

        verify(lectureDao, times(1)).deleteById(lectureWithId.getId());
    }
}