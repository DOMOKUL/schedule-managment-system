package com.company.schedule.management.system.dao;

import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.dao.impl.GroupDaoImpl;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class GroupDaoImplTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Group TEST_GROUP = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);

    @Autowired
    private GroupDaoImpl groupDao;

    @Test
    void create_shouldReturnCorrectGroup_whenInputCorrectData() {
        Group actual = groupDao.create(new Group(null, TEST_FACULTY, null, null));
        Group expected = new Group(1L, null, TEST_FACULTY, null, null);
        assertEquals(expected, actual);
        //same
    }

    @Test
    void create_shouldThrowException_whenInputExistId() {
        Group testGroup = new Group("BSBO-04-20", TEST_FACULTY, null, null);
        assertThrows(DataIntegrityViolationException.class, () ->
                groupDao.create(testGroup));
    }

    @Test
    void findById_shouldReturnCorrectGroup_whenInputExistId() {
        Group testGroup = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);
        assertEquals(testGroup, groupDao.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentGroupId() {
        assertThrows(DaoException.class, () ->
                groupDao.findById(10000L));
    }

    @Test
    void findAll_shouldReturnGroupsList() {
        List<Group> groups = groupDao.findAll();
        assertFalse(groups.isEmpty());
    }

    @Test
    void update_shouldUpdateGroup_whenInputExistId() {
        Group actual = groupDao.update(TEST_GROUP);
        assertEquals(TEST_GROUP, actual);
    }

    @Test
    void update_shouldThrowDaoException_whenInputNotExistGroupId() {
        Group testGroup = new Group("BSBO-04-20", TEST_FACULTY, null, null);
        assertThrows(DataIntegrityViolationException.class, () ->
                groupDao.update(testGroup));
    }

    @Test
    void delete_shouldDeleteGroup_whenInputExistId() {
        boolean actual = groupDao.deleteById(TEST_GROUP.getId());
        assertTrue(actual);
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistGroupId() {
        assertThrows(DaoException.class, () ->
                groupDao.deleteById(100L));
    }
}