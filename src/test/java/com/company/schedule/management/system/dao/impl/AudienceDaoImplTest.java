package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.config.HibernateConfiguration;
import com.company.schedule.management.system.model.Audience;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = { HibernateConfiguration.class })
@Transactional
class AudienceDaoImplTest extends BaseIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

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
        Session session = sessionFactory.getCurrentSession();
        audienceDao.delete(1L);
        Audience testAudience = new Audience();
        testAudience.setId(1L);
        Audience searchAudience = session.find(Audience.class,1);
        List<Audience> audiences = new ArrayList<>();
        audiences.add(searchAudience);
        assertFalse(audiences.isEmpty());
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