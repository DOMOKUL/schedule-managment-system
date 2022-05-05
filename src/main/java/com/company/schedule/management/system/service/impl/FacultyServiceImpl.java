package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyDao facultyDao;

    @Override
    public Faculty saveFaculty(Faculty faculty) {
        try {
            return facultyDao.create(faculty);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Faculty getFacultyById(Long id) {
        LOGGER.debug("Faculty at id = {} found: {}", id, facultyDao.findById(id).get());
        return facultyDao.findById(id).orElseThrow(() -> new ServiceException("Faculty with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Faculty> getAllFaculties() {
        LOGGER.debug("Faculties found:{}", facultyDao.findAll());
        try {
            return facultyDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        LOGGER.debug("Faculty has been updated: {}", faculty);
        try {
            return facultyDao.update(faculty);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteFacultyById(Long id) {
        if (facultyDao.findById(id).isPresent()) {
            LOGGER.debug("Faculty with id: {} has been deleted", id);
        }
        facultyDao.findById(id).orElseThrow(() -> new ServiceException("Faculty with id: " + id + " doesn't exist"));
        facultyDao.deleteById(id);
    }

    @Override
    public List<Faculty> getFacultiesForGroups(List<Group> allGroups) {
        LOGGER.debug("Getting faculties for groups {}", allGroups);
        List<Faculty> faculties = allGroups.stream()
                .map(Group::getFaculty)
                .collect(Collectors.toList());
        LOGGER.info("Faculties for groups {} received successful", allGroups);
        return faculties;
    }
}
