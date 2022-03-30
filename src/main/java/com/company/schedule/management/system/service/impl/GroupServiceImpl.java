package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.GroupDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.GroupService;
import com.company.schedule.management.system.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public Group saveGroup(Group group) {
        try {
            return groupDao.create(group);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Group getGroupById(Long id) {
        return groupDao.findById(id).orElseThrow(() -> new ServiceException("Group with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Group> getAllGroups() {
        try {
            return groupDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Group updateGroup(Group group) {
        try {
            return groupDao.update(group);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public boolean deleteGroupById(Long id) {
        try {
            return groupDao.deleteById(id);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }
}