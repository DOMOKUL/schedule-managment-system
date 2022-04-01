package com.company.schedule.management.system.dao;

import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class AudienceDaoImplTest extends BaseIntegrationTest {

    private static final Audience TEST_AUDIENCE = new Audience(10L, null, null, null);
    private static final Subject TEST_SUBJECT = new Subject(10L, "math", null);
    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Group TEST_GROUP = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);

    private static final Lecture TEST_LECTURE = new Lecture(10L, 10, Date.valueOf("1988-09-29"),
            TEST_AUDIENCE, TEST_GROUP,
            new Lesson(10L, 10, LocalTime.of(13, 0, 0),
                    Duration.ofMinutes(90L), TEST_SUBJECT, null),
            new Teacher(10L, TEST_FACULTY, null));

    @Autowired
    private AudienceDao audienceDao;

    @Test
    void create_shouldReturnCorrectAudience_whenInputCorrectData() {
        Audience actual = audienceDao.create(new Audience(null, null, null));
        Audience expected = new Audience(1L, null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    void create_shouldThrowException_whenInputExistId() {
        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                audienceDao.create(TEST_AUDIENCE));
    }

    @Test
    void findById_shouldReturnCorrectAudience_whenInputExistId() {
        Audience testAudience = new Audience(10L, 10, 10, List.of(TEST_LECTURE));
        assertEquals(testAudience, audienceDao.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentAudienceId() {
        assertEquals(Optional.empty(), audienceDao.findById(10000L));
    }

    @Test
    void findAll_shouldReturnAudiencesList() {
        List<Audience> audiences = audienceDao.findAll();
        assertFalse(audiences.isEmpty());
    }

    @Test
    void update_shouldUpdateAudience_whenInputExistId() {
        Audience actual = audienceDao.update(TEST_AUDIENCE);
        assertEquals(TEST_AUDIENCE, actual);
    }

    @Test
    void delete_shouldDeleteFaculty_whenInputExistId() {
        boolean actual = audienceDao.deleteById(TEST_AUDIENCE.getId());
        assertTrue(actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistAudienceId() {
        assertThrows(DaoException.class, () ->
                audienceDao.deleteById(100L));
    }
}