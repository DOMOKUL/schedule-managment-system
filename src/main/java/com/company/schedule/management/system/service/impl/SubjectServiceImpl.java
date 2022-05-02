package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.SubjectService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDao subjectDao;

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
    public void deleteSubjectById(Long id) {
        subjectDao.findById(id).orElseThrow(() -> new ServiceException("Subject with id: " + id + " doesn't exist"));
        subjectDao.deleteById(id);
    }
}
