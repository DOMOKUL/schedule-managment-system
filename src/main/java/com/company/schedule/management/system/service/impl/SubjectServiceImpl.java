package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.SubjectService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectServiceImpl.class);
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
        LOGGER.debug("Subject at id = {} found: {}", id, subjectDao.findById(id).get());
        return subjectDao.findById(id).orElseThrow(() -> new ServiceException("Subject with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Subject> getAllSubjects() {
        LOGGER.debug("Subjects found:{}", subjectDao.findAll());
        try {
            return subjectDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Subject updateSubject(Subject subject) {
        LOGGER.debug("Subject has been updated: {}", subject);
        try {
            return subjectDao.update(subject);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteSubjectById(Long id) {
        if (subjectDao.findById(id).isPresent()) {
            LOGGER.debug("Subject with id: {} has been deleted", id);
        }
        subjectDao.findById(id).orElseThrow(() -> new ServiceException("Subject with id: " + id + " doesn't exist"));
        subjectDao.deleteById(id);
    }
}
