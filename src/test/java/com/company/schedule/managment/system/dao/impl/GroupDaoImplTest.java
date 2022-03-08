package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.models.Faculty;
import com.company.schedule.managment.system.models.Group;
import com.company.schedule.managment.system.models.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class GroupDaoImplTest {

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("sql/fill-table.sql");
    private GroupDaoImpl groupDao;

    private static final Group TEST_GROUP = new Group("BABO-02-19",
            new Faculty(1L, "INDA", null, null),
            null,
            List.of(new Lecture(2L, 2, Date.valueOf("2019-01-26"),
                    null, null, null, null)));

    private static final Group TEST_GROUP_WITH_ID = new Group(1L,"BABO-02-19",
            new Faculty(1L, "INDA", null, null),
            null,
            List.of(new Lecture(2L, 2, Date.valueOf("2019-01-26"),
                    null, null, null, null)));

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        groupDao = new GroupDaoImpl(dataSource);
        container.start();
    }

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