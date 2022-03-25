package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.SubjectService;
import com.company.schedule.management.system.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDao subjectDao;

    @Autowired
    public SubjectServiceImpl(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    @Override
    public Subject saveSubject(Subject subject) {
        try {
            return subjectDao.create(subject);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectDao.findById(id).orElseThrow(() -> new ServiceException("Subject with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Subject> getAllSubjects() {
        try {
            return subjectDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Subject updateSubject(Subject subject) {
        try {
            return subjectDao.update(subject);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public boolean deleteSubjectById(Long id) {
        try {
            return subjectDao.deleteById(id);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public List<Subject> saveAllSubjects(List<Subject> subjects) {
        List<Subject> result = new ArrayList<>();
        try {
            subjects.forEach(subject -> result.add(saveSubject(subject)));
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
        return result;
    }
}
