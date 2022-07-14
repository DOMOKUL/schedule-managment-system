package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.repository.GroupRepository;
import com.company.schedule.management.system.service.GroupService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private final GroupRepository groupRepository;

    @Override
    public Group saveGroup(Group group) {
        try {
            return groupRepository.saveAndFlush(group);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Group getGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        LOGGER.debug("Group at id = {} found: {}", id, group.get());
        return group.orElseThrow(() -> new ServiceException("Group with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Group> getAllGroups() {
        LOGGER.debug("Groups found:{}", groupRepository.findAll());
        return groupRepository.findAll();
    }

    @Override
    public Group updateGroup(Group group) {
        LOGGER.debug("Group has been updated: {}", group);
        try {
            return groupRepository.saveAndFlush(group);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteGroupById(Long id) {
        if (groupRepository.findById(id).isPresent()) {
            groupRepository.deleteById(id);
            LOGGER.debug("Group with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Group with id: " + id + " doesn't exist");
        }

    }
}
