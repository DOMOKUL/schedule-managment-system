package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Audience;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Transactional
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
        List<Audience> audiences = audienceDao.findAll();
        assertFalse(audiences.isEmpty());
    }

    @Test
    void update_shouldUpdateAudience_whenInputExistId() {

    }

    @Test
    void delete_shouldDeleteAudience_whenInputExistId() {
    }
}