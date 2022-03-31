package com.company.schedule.management.system.service;

import com.company.schedule.management.system.dao.GroupDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void saveGroup_shouldReturnGroup_whenInputCorrectData() {
        when(groupDao.create(groupWithoutId)).thenReturn(groupWithId);
        groupServiceImpl.saveGroup(groupWithoutId);
        verify(groupDao, times(1)).create(groupWithoutId);
    }

    @Test
    void saveGroup_shouldThrowException_whenInputExistGroup() {
        when(groupDao.create(groupWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> groupServiceImpl.saveGroup(groupWithId));
    }

    @Test
    void getGroupById_shouldReturnGroup_whenInputExistId() {
        when(groupDao.findById(1L)).thenReturn(Optional.ofNullable(groupWithId));
        assertThat(groupServiceImpl.getGroupById(groupWithId.getId())).isEqualTo(groupWithId);
    }

    @Test
    void getGroupById_shouldThrowException_whenInputNonExistGroupId() {
        when(groupDao.findById(groupWithId.getId())).thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> groupServiceImpl.getGroupById(groupWithId.getId()));
    }

    @Test
    void getAllGroups_shouldReturnListGroups() {
        when(groupDao.findAll()).thenReturn(groupList);
        List<Group> groups = groupServiceImpl.getAllGroups();

        assertEquals(groupList, groups);
        verify(groupDao, times(1)).findAll();
    }

    @Test
    void updateGroup_shouldReturnUpdatedGroup_whenInputExistGroup() {
        Group expected = new Group(1L, "BASO-04-19", new Faculty(1L, "IT", null, null),
                null, null);

        when(groupDao.update(expected)).thenReturn(expected);
        Group actual = groupServiceImpl.updateGroup(expected);

        assertEquals(expected, actual);
        verify(groupDao, times(1)).update(expected);
    }

    @Test
    void updateGroup_shouldThrowException_whenInputNonExistGroup() {
        when(groupDao.update(groupWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> groupServiceImpl.updateGroup(groupWithId));
    }

    @Test
    void deleteGroupById_shouldReturnTrue_whenInputExistGroupId() {
        when(groupDao.deleteById(groupWithId.getId())).thenReturn(true);

        assertTrue(groupServiceImpl.deleteGroupById(groupWithId.getId()));

        verify(groupDao, times(1)).deleteById(groupWithId.getId());
    }

    @Test
    void deleteGroupById_shouldThrowException_whenInputNonExistGroupId() {
        when(groupDao.deleteById(groupWithId.getId())).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> groupServiceImpl.deleteGroupById(groupWithId.getId()));
    }
}