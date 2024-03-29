package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class LessonRepositoryTest extends BaseIntegrationTest {

    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);
    private static final Lesson TEST_LESSON = new Lesson(10L, 10, Time.valueOf(LocalTime.of(13, 0, 0)),
            Duration.ofMinutes(90L), TEST_SUBJECT, null);

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void create_shouldReturnCorrectLesson_whenInputCorrectData() {
        Lesson expected = new Lesson(1L, 10, null, null, TEST_SUBJECT, null);
        Lesson actual = lessonRepository.saveAndFlush(new Lesson(10, null, null, TEST_SUBJECT, null));
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectLesson_whenInputExistId() {
        assertEquals(TEST_LESSON, lessonRepository.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentLessonId() {
        assertEquals(Optional.empty(), lessonRepository.findById(10000L));
    }

    @Test
    void findAll_shouldReturnLessonsList() {
        List<Lesson> lessons = lessonRepository.findAll();
        assertFalse(lessons.isEmpty());
    }

    @Test
    void update_shouldUpdateLesson_whenInputExistId() {
        Lesson actual = lessonRepository.saveAndFlush(TEST_LESSON);
        assertEquals(TEST_LESSON, actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistLessonId() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                lessonRepository.deleteById(100L));
    }
}