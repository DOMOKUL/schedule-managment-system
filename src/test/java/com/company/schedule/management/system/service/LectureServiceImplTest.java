package com.company.schedule.management.system.service;

import com.company.schedule.management.system.repository.LectureRepository;
import com.company.schedule.management.system.repository.exception.DaoException;
import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.LectureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {LectureServiceImpl.class})
class LectureServiceImplTest {

    private static final Audience TEST_AUDIENCE = new Audience(10L, 10, 10, null);
    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);
    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Group TEST_GROUP = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);

    @Autowired
    LectureServiceImpl lectureServiceImpl;
    @MockBean
    LectureRepository lectureRepository;

    private Lecture lectureWithId;
    private Lecture lectureWithoutId;
    private List<Lecture> lectureList;

    @BeforeEach
    void setUp() {
        lectureWithId = new Lecture(1L, 10, Date.valueOf("1988-09-29"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 10, Time.valueOf(LocalTime.of(13, 0, 0)),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null));
        lectureWithoutId = new Lecture(10, Date.valueOf("1988-09-29"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 10, Time.valueOf(LocalTime.of(13, 0, 0)),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null));
        lectureList = List.of(lectureWithId);
    }

    @Test
    void saveLecture_shouldReturnLecture_whenInputCorrectData() {
        when(lectureRepository.saveAndFlush(lectureWithoutId)).thenReturn(lectureWithId);
        lectureServiceImpl.saveLecture(lectureWithoutId);
        verify(lectureRepository, times(1)).saveAndFlush(lectureWithoutId);
    }

    @Test
    void saveLecture_shouldThrowException_whenInputExistLecture() {
        when(lectureRepository.saveAndFlush(lectureWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> lectureServiceImpl.saveLecture(lectureWithId));
    }

    @Test
    void getLectureById_shouldReturnLecture_whenInputExistLectureId() {
        when(lectureRepository.findById(1L)).thenReturn(Optional.ofNullable(lectureWithId));
        assertThat(lectureServiceImpl.getLectureById(lectureWithId.getId())).isEqualTo(lectureWithId);
    }

    @Test
    void getLectureById_shouldThrowException_whenInputNonExistLectureId() {
        when(lectureRepository.findById(lectureWithId.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> lectureServiceImpl.getLectureById(lectureWithId.getId()));
    }

    @Test
    void getAllLectures_shouldReturnListLectures() {
        when(lectureRepository.findAll()).thenReturn(lectureList);
        List<Lecture> lectures = lectureServiceImpl.getAllLectures();

        assertEquals(lectureList, lectures);
        verify(lectureRepository, times(2)).findAll();
    }

    @Test
    void updateLecture_shouldReturnUpdatedLecture_whenInputExistLectureId() {
        Lecture expected = new Lecture(1L, 11, Date.valueOf("2022-09-29"),
                TEST_AUDIENCE, TEST_GROUP,
                new Lesson(10L, 10, Time.valueOf(LocalTime.of(13, 0, 0)),
                        Duration.ofMinutes(90L), TEST_SUBJECT, null),
                new Teacher(10L, TEST_FACULTY, null));

        when(lectureRepository.saveAndFlush(expected)).thenReturn(expected);
        Lecture actual = lectureServiceImpl.updateLecture(expected);

        assertEquals(expected, actual);
        verify(lectureRepository, times(1)).saveAndFlush(expected);
    }

    @Test
    void updateLecture_shouldThrowException_whenInputNonExistLecture() {
        when(lectureRepository.saveAndFlush(lectureWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> lectureServiceImpl.updateLecture(lectureWithId));
    }

    @Test
    void deleteLectureById_shouldReturnTrue_whenInputExistLectureId() {
        when(lectureRepository.findById(lectureWithId.getId())).thenReturn(Optional.of(lectureWithId));

        lectureServiceImpl.deleteLectureById(lectureWithId.getId());

        verify(lectureRepository, times(1)).deleteById(lectureWithId.getId());
    }
}