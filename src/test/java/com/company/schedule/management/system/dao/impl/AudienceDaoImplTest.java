package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.Audience;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class AudienceDaoImplTest extends BaseIntegrationTest {

    @Autowired
    private AudienceDaoImpl audienceDao;

    @Test
    void create_shouldReturnCorrectIdAudience_whenInputCorrectData() {
        Audience actual = audienceDao.create(new Audience(1L, 1, 1, null));
        Audience expected = new Audience(1L, 1, 1, null);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectAudience_whenInputExistId() {
        Audience testAudience = new Audience(10L, 10, 10, null);
        assertEquals(testAudience, audienceDao.findById(10L));
    }

    @Test
    void findAll_shouldReturnAudienceList() {
        List<Audience> audiences = audienceDao.findAll();
        assertFalse(audiences.isEmpty());
    }

    @Test
    void update_shouldUpdateAudience_whenInputExistId() {
        Audience testAudience = new Audience(10L, 11, 11, null);
        boolean actual = audienceDao.update(testAudience);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteAudience_whenInputExistId() {
        boolean actual = audienceDao.deleteById(10L);
        assertTrue(actual);
    }
}