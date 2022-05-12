package com.company.schedule.management.system.service;

import com.company.schedule.management.system.repository.GroupRepository;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GroupServiceImpl.class})
class GroupServiceImplTest {

    @Autowired
    private GroupServiceImpl groupServiceImpl;
    @MockBean
    private GroupRepository groupRepository;

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
        when(groupRepository.saveAndFlush(groupWithoutId)).thenReturn(groupWithId);
        groupServiceImpl.saveGroup(groupWithoutId);
        verify(groupRepository, times(1)).saveAndFlush(groupWithoutId);
    }

    @Test
    void saveGroup_shouldThrowException_whenInputExistGroup() {
        when(groupRepository.saveAndFlush(groupWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> groupServiceImpl.saveGroup(groupWithId));
    }

    @Test
    void getGroupById_shouldReturnGroup_whenInputExistId() {
        when(groupRepository.findById(1L)).thenReturn(Optional.ofNullable(groupWithId));
        assertThat(groupServiceImpl.getGroupById(groupWithId.getId())).isEqualTo(groupWithId);
    }

    @Test
    void getGroupById_shouldThrowException_whenInputNonExistGroupId() {
        when(groupRepository.findById(groupWithId.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> groupServiceImpl.getGroupById(groupWithId.getId()));
    }

    @Test
    void getAllGroups_shouldReturnListGroups() {
        when(groupRepository.findAll()).thenReturn(groupList);
        List<Group> groups = groupServiceImpl.getAllGroups();

        assertEquals(groupList, groups);
        verify(groupRepository, times(2)).findAll();
    }

    @Test
    void updateGroup_shouldReturnUpdatedGroup_whenInputExistGroup() {
        Group expected = new Group(1L, "BASO-04-19", new Faculty(1L, "IT", null, null),
                null, null);

        when(groupRepository.saveAndFlush(expected)).thenReturn(expected);
        Group actual = groupServiceImpl.updateGroup(expected);

        assertEquals(expected, actual);
        verify(groupRepository, times(1)).saveAndFlush(expected);
    }

    @Test
    void updateGroup_shouldThrowException_whenInputNonExistGroup() {
        when(groupRepository.saveAndFlush(groupWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> groupServiceImpl.updateGroup(groupWithId));
    }

    @Test
    void deleteGroupById_shouldReturnTrue_whenInputExistGroupId() {
        when(groupRepository.findById(groupWithId.getId())).thenReturn(Optional.of(groupWithId));

        groupServiceImpl.deleteGroupById(groupWithId.getId());

        verify(groupRepository, times(1)).deleteById(groupWithId.getId());
    }
}