package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Group;

import java.util.List;

public interface GroupService {

    Group saveGroup(Group group);

    Group getGroupById(Long id);

    List<Group> getAllGroups();

    Group updateGroup(Group group);

    boolean deleteGroupById(Long id);
}
