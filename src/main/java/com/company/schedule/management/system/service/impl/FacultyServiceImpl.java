package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.repository.FacultyRepository;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    @Override
    public Faculty saveFaculty(Faculty faculty) {
        try {
            return facultyRepository.saveAndFlush(faculty);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Faculty getFacultyById(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        LOGGER.debug("Faculty at id = {} found: {}", id, faculty.get());
        return faculty.orElseThrow(() -> new ServiceException("Faculty with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Faculty> getAllFaculties() {
        LOGGER.debug("Faculties found:{}", facultyRepository.findAll());
        return facultyRepository.findAll();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        LOGGER.debug("Faculty has been updated: {}", faculty);
        try {
            return facultyRepository.saveAndFlush(faculty);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteFacultyById(Long id) {
        if (facultyRepository.findById(id).isPresent()) {
            facultyRepository.deleteById(id);
            LOGGER.debug("Faculty with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Audience with id: " + id + " doesn't exist");
        }

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
