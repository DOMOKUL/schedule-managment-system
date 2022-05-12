package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class GroupRepositoryTest extends BaseIntegrationTest {

    private static final Faculty TEST_FACULTY = new Faculty(10L, "IKBSP", null, null);
    private static final Group TEST_GROUP = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void create_shouldReturnCorrectGroup_whenInputCorrectData() {
        Group actual = groupRepository.saveAndFlush(new Group(null, TEST_FACULTY, null, null));
        Group expected = new Group(1L, null, TEST_FACULTY, null, null);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldReturnCorrectGroup_whenInputExistId() {
        Group testGroup = new Group(10L, "BSBO-04-20", TEST_FACULTY, null, null);
        assertEquals(testGroup, groupRepository.findById(10L).get());
    }

    @Test
    void findById_shouldThrowDaoException_whenInputNonExistentGroupId() {
        assertEquals(Optional.empty(), groupRepository.findById(10000L));
    }

    @Test
    void findAll_shouldReturnGroupsList() {
        List<Group> groups = groupRepository.findAll();
        assertFalse(groups.isEmpty());
    }

    @Test
    void update_shouldUpdateGroup_whenInputExistId() {
        Group actual = groupRepository.saveAndFlush(TEST_GROUP);
        assertEquals(TEST_GROUP, actual);
    }

    @Test
    void delete_shouldDeleteGroup_whenInputExistId() {
        if(groupRepository.findById(TEST_GROUP.getId()).isPresent()){
            groupRepository.deleteById(TEST_GROUP.getId());
        }

        assertTrue(groupRepository.findById(TEST_GROUP.getId()).isEmpty());
    }

    @Test
    void delete_shouldThrowDaoException_whenInputNotExistGroupId() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                groupRepository.deleteById(100L));
    }
}