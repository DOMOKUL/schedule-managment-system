package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.models.Audience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class AudienceDaoImplTest {

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");
    private AudienceDaoImpl audienceDao;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        audienceDao = new AudienceDaoImpl(dataSource);
        container.start();
    }

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