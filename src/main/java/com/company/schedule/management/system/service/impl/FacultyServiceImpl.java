package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyDao facultyDao;

    @Autowired
    public FacultyServiceImpl(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

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
        return facultyDao.findById(id).orElseThrow(() -> new ServiceException("Faculty with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Faculty> getAllFaculties() {
        try {
            return facultyDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        try {
            return facultyDao.update(faculty);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public boolean deleteFacultyById(Long id) {
        try {
            return facultyDao.deleteById(id);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }
}
