package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.model.Audience;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AudienceDaoImplTest extends BaseIntegrationTest {

    private final AudienceDaoImpl audienceDao = new AudienceDaoImpl(DATA_SOURCE);

    @Test
    void create_shouldReturnCorrectIdAudience_whenInputCorrectData() {
        Audience testAudience = new Audience(10, 10);
        audienceDao.delete(audienceDao.findById(1L).getId());
        audienceDao.create(testAudience);
        Audience expected = new Audience(1L, 10, 10);
        assertEquals(expected, audienceDao.findById(1L));
    }

    @Test
    void findById_shouldReturnCorrectAudience_whenInputExistId() {
        Audience testAudience = new Audience(1L, 1, 1);
        assertEquals(testAudience, audienceDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnAudienceList() {
        List<Audience> courses = audienceDao.findAll();
        assertFalse(courses.isEmpty());
    }

    @Test
    void update_shouldUpdateAudience_whenInputExistId() {
        Audience testAudience = new Audience(1L, 11, 11);
        boolean actual = audienceDao.update(testAudience);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteAudience_whenInputExistId() {
        boolean actual = audienceDao.delete(1L);
        assertTrue(actual);
    }
}