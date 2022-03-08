package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.model.Faculty;
import com.company.schedule.managment.system.model.Group;
import com.company.schedule.managment.system.model.Lecture;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupDaoImplTest extends BaseIntegrationTest {

    private static final Group TEST_GROUP = new Group("BABO-02-19",
            new Faculty(1L, "INDA", null, null),
            null,
            List.of(new Lecture(2L, 2, Date.valueOf("2019-01-26"),
                    null, null, null, null)));
    private static final Group TEST_GROUP_WITH_ID = new Group(1L, "BABO-02-19",
            new Faculty(1L, "INDA", null, null),
            null,
            List.of(new Lecture(2L, 2, Date.valueOf("2019-01-26"),
                    null, null, null, null)));
    private final GroupDaoImpl groupDao = new GroupDaoImpl(DATA_SOURCE);

    @Test
    void create_shouldReturnCorrectGroup_whenInputCorrectData() {
        groupDao.delete(1L);
        assertEquals(1L, groupDao.create(TEST_GROUP).getId());
    }

    @Test
    void findById_shouldReturnCorrectGroup_whenInputExistId() {
        Group testGroup = new Group(1L, "BSBO-04-20", null, null, null);
        assertEquals(testGroup, groupDao.findById(1L));
    }

    @Test
    void findAll_shouldReturnGroupsList() {
        List<Group> groups = groupDao.findAll();
        assertFalse(groups.isEmpty());
    }

    @Test
    void update_shouldUpdateGroup_whenInputExistId() {
        boolean actual = groupDao.update(TEST_GROUP_WITH_ID);
        assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteGroup_whenInputExistId() {
        boolean actual = groupDao.delete(1L);
        assertTrue(actual);
    }
}