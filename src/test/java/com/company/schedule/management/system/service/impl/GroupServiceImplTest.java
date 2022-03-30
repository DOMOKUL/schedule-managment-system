package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.GroupDao;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GroupServiceImpl.class})
class GroupServiceImplTest {

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @MockBean
    private GroupDao groupDao;

    private Group groupWithId;
    private Group groupWithoutId;
    private List<Group> groupList;

    @BeforeEach
    void setUp() {
        groupWithId = new Group(1L, "BSBO-04-19", new Faculty(1L, "IKBSP", null, null),
                null, null);
        groupWithoutId = new Group("BSBO-04-19", new Faculty(1L, "IKBSP", null, null),
                null, null);
        groupList = List.of(groupWithId);
    }

    @AfterEach
    public void tearDown() {
        groupWithId = groupWithoutId = null;
        groupList = null;
    }

    @Test
    void saveGroup() {
        when(groupDao.create(groupWithoutId)).thenReturn(groupWithId);
        groupServiceImpl.saveGroup(groupWithoutId);
        verify(groupDao, times(1)).create(groupWithoutId);
    }

    @Test
    void getGroupById() {
        when(groupDao.findById(1L)).thenReturn(Optional.ofNullable(groupWithId));
        assertThat(groupServiceImpl.getGroupById(groupWithId.getId())).isEqualTo(groupWithId);
    }

    @Test
    void getAllGroups() {
        when(groupDao.findAll()).thenReturn(groupList);
        List<Group> groups = groupServiceImpl.getAllGroups();

        assertEquals(groupList, groups);
        verify(groupDao, times(1)).findAll();
    }

    @Test
    void updateGroup() {
        Group expected = new Group(1L, "BASO-04-19", new Faculty(1L, "IT", null, null),
                null, null);

        when(groupDao.update(expected)).thenReturn(expected);
        Group actual = groupServiceImpl.updateGroup(expected);

        assertEquals(expected, actual);
        verify(groupDao, times(1)).update(expected);
    }

    @Test
    void deleteGroupById() {
        when(groupDao.deleteById(groupWithId.getId())).thenReturn(true);

        assertTrue(groupServiceImpl.deleteGroupById(groupWithId.getId()));

        verify(groupDao, times(1)).deleteById(groupWithId.getId());
    }
}