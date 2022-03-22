package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class GroupDaoImplTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, null, null, null);
    private static final Group TEST_GROUP = new Group(10L, null, TEST_FACULTY, null, null);

    @Autowired
    private GroupDaoImpl groupDao;

    @Test
    void create_shouldReturnCorrectGroup_whenInputCorrectData() {
        Group actual = groupDao.create(new Group(1L, null, TEST_FACULTY,null,null));
        Group expected = new Group(1L, null, TEST_FACULTY,null,null);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectGroup_whenInputExistId() {
        Group testGroup = new Group(1L, "BSBO-04-20", null, null, null);
        assertEquals(testGroup, groupDao.findById(10L));
    }

    @Test
    void findAll_shouldReturnGroupsList() {
        List<Group> groups = groupDao.findAll();
        assertFalse(groups.isEmpty());
    }

    @Test
    void update_shouldUpdateGroup_whenInputExistId() {
        boolean actual = groupDao.update(TEST_GROUP);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteGroup_whenInputExistId() {
        boolean actual = groupDao.deleteById(10L);
        assertTrue(actual);
    }
}